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

import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsIntTest;
import srojak.sql.MappedObjectException;

/**
 * @author Stephen
 *
 */
public interface ObjectMapping {

	ClassKey mappedObjectClass();
	FlagsIntTest getFlags();
	boolean isValid();
	void process() throws MappedObjectException;
	ResultSet query(Statement stmt) throws SQLException;
	<T extends AbstractMappedObject> T createInstance() throws MappedObjectException;
	<T extends AbstractMappedObject> void populateFrom(ResultSet rset, T instance) 
			throws SQLException, MappedObjectException;
	
	static final int FLAGS_VALID = 0x1;
	static final int FLAGS_PROCESSED = 0x2;
}
