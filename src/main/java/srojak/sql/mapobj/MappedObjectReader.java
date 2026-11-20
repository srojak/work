/**
 * Copyright © 2026 Stephen Rojak.
 * 
 * This file is part of the srojak Java portfolio.
 * 
 * The srojak Java portfolio is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 3 of the License.
 * 
 * The srojak Java portfolio is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this portfolio.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package srojak.sql.mapobj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.sql.MappedObjectException;

/**
 * @author Stephen
 *
 */
public class MappedObjectReader {
	
	private <T extends AbstractMappedObject> void readFromStatement(Statement stmt, 
			MappedObjectFactory<T> factory, Consumer<T> consumer)
					throws SQLException, MappedObjectException {
		ResultSet rset = factory.executeReadQuery(stmt);
		while (rset.next()) {
			T instance = factory.createInstance();
			instance.fillFromResultSet(rset);
			consumer.accept(instance);
		}
	}
	
	private void validateConcurrency(int concurrency) {
		// whatever the allowable values of concurrency are,
		// we know they are positive
		if (concurrency <= 0) {
			throw new IllegalArgumentException("invalid concurrency");
		}
	}

	public <T extends AbstractMappedObject> List<T> readFrom(Statement stmt, MappedObjectFactory<T> factory) 
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(factory, "factory");
		if (stmt.isClosed()) {
			throw new IllegalArgumentException("stmt is closed");
		}
		List<T> listObjs = new LinkedList<T>();
		readFromStatement(stmt, factory, i -> listObjs.add(i));
		return listObjs;
	}
	
	public <T extends AbstractMappedObject> List<T> readFrom(Connection connect,
			MappedObjectFactory<T> factory, int concurrency)
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(connect, "connect");
		Objects.requireNonNull(factory, "factory");
		validateConcurrency(concurrency);
		if (connect.isClosed()) {
			throw new IllegalArgumentException("connection is closed");
		}
		try (Statement stmt = connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, concurrency)) {
			List<T> listObjs = new LinkedList<T>();
			readFromStatement(stmt, factory, i -> listObjs.add(i));
			return listObjs;
		} catch (SQLException exc) {
			throw exc;
		}
	}
	
	public <T extends AbstractMappedObject> void readFrom(Connection connect,
			MappedObjectFactory<T> factory, int concurrency, Consumer<T> consumer)
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(connect, "connect");
		Objects.requireNonNull(factory, "factory");
		validateConcurrency(concurrency);
		Objects.requireNonNull(consumer, "consumer");
		if (connect.isClosed()) {
			throw new IllegalArgumentException("connection is closed");
		}
		try (Statement stmt = connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, concurrency)) {
			readFromStatement(stmt, factory, consumer);
		} catch (SQLException exc) {
			throw exc;
		}
	}
}
