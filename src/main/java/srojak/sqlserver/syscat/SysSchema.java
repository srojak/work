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
package srojak.sqlserver.syscat;

import srojak.numerics.nullable.NullableInt;
import srojak.sql.MappedObjectException;
import srojak.sql.NullableCollatedString;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.ObjectMapping;
import srojak.sql.mapobj.SingleTableSource;
import srojak.sql.mapobj.TextColumnSource;
import srojak.sql.mapobj.UseSQLCollation;

/**
 * @author Stephen
 *
 */
@SingleTableSource(name = "schemas", schema = "sys", canInsert = false,
					canUpdate = false, canDelete = false)
public class SysSchema 
		extends AbstractMappedObject {
	
	@ColumnSource(name = "schema_id")
	@KeyColumn(ordinal = 1)
	private NullableInt _idSchema;
	
	@TextColumnSource(name = "name", isUnicode = true, maxLength = 128)
	@UseSQLCollation("SQL_Latin1_General_CP1_CI_AS")
	private NullableCollatedString _ncstrName;

	@ColumnSource(name = "principal_id")
	private NullableInt _idPrincipal;

	/**
	 * @throws MappedObjectException 
	 * 
	 */
	public SysSchema() {
		super();
	}

	/**
	 * @param mapping
	 * @throws MappedObjectException
	 */
	public SysSchema(ObjectMapping mapping) throws MappedObjectException {
		super(mapping);
	}

	public NullableInt getSchemaID() {
		return _idSchema;
	}

	public void setSchemaID(NullableInt _idSchema) {
		this._idSchema = _idSchema;
	}

	public NullableCollatedString getName() {
		return _ncstrName;
	}

	public void setName(NullableCollatedString _ncstrName) {
		this._ncstrName = _ncstrName;
	}

	public NullableInt getPrincipalID() {
		return _idPrincipal;
	}

	public void setPrincipalID(NullableInt _idPrincipal) {
		this._idPrincipal = _idPrincipal;
	}
}
