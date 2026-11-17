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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.impl.MappedObjectCatalog;

/**
 * @author Stephen
 *
 */
public class MappedObjectReader {

	public <T extends AbstractMappedObject> List<T> readFrom(Statement stmt, MappedObjectFactory<T> factory) 
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(factory, "factory");
		if (stmt.isClosed()) {
			throw new IllegalArgumentException("stmt is closed");
		}
		List<T> listObjs = new LinkedList<T>();
		ResultSet rset = factory.executeReadQuery(stmt);
		while (rset.next()) {
			T instance = factory.createInstance();
			instance.fillFromResultSet(rset);
			listObjs.add(instance);
		}
		return listObjs;
	}
}
