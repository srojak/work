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
package srojak.sql.names;

import srojak.sql.DatabaseObject;

/**
 * @author Stephen
 *
 */
public final class DatabaseSchemaObjectName 
		extends AbstractSQLMultiPartName 
		implements DatabaseObject {

	/**
	 * @param parts
	 */
	public DatabaseSchemaObjectName(String strDatabase, String strSchema, String strObject) {
		super(3);
		SQLNamePart ptDatabase = new SQLNamePart(strDatabase);
		SQLNamePart ptSchema = new SQLNamePart(strSchema);
		SQLNamePart ptObject = new SQLNamePart(strObject);
		_listNames.add(0, ptDatabase);
		_listNames.add(1, ptSchema);
		_listNames.add(2, ptObject);
	}
	
	public SQLNamePart getDatabaseName() {
		return _listNames.get(0);
	}
	
	public SQLNamePart getSchemaName() {
		return _listNames.get(1);
	}
	
	public SQLNamePart getObjectName() {
		return _listNames.get(2);
	}

	@Override
	public String fullName() {
		return assemble();
	}
}
