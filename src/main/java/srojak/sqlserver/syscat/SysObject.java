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

import srojak.numerics.nullable.NullableDateTime;
import srojak.numerics.nullable.NullableInt;
import srojak.sql.MappedObjectException;
import srojak.sql.NullableCollatedString;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.ColumnTimeZone;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.ObjectMapping;
import srojak.sql.mapobj.SingleTableSource;
import srojak.sql.mapobj.TextColumnSource;
import srojak.sql.mapobj.UseSQLCollation;

/**
 * @author Stephen
 *
 */
@SingleTableSource(name = "objects", schema = "sys", canInsert = false,
					canUpdate = false, canDelete = false,
					restriction = "NOT type IN ( 'S' )")
public class SysObject 
		extends AbstractMappedObject {
	
	@ColumnSource(name = "object_id")
	@KeyColumn(ordinal = 1)
	private NullableInt _idObject;
	
	@TextColumnSource(name = "name", isUnicode = true, maxLength = 128)
	@UseSQLCollation("SQL_Latin1_General_CP1_CI_AS")
	private NullableCollatedString _ncstrName;
	
	@ColumnSource(name = "schema_id")
	private NullableInt _idSchema;
	
	@ColumnSource(name = "parent_object_id")
	private NullableInt _idParentObject;
	
	@TextColumnSource(name = "type", isFixedLength = true, maxLength = 2)
	@UseSQLCollation(value = "Latin1_General_CI_AS_KS_WS", delta = 2)
	private NullableCollatedString _ncstrType;
	
	@ColumnSource(name = "create_date")
	@ColumnTimeZone("America/Chicago")
	private NullableDateTime _ndtCreated;
	
	@ColumnSource(name = "modify_date")
	@ColumnTimeZone("America/Chicago")
	private NullableDateTime _ndtModified;

	/**
	 * 
	 */
	public SysObject() {
		super();
	}

	/**
	 * @param mapping
	 * @throws MappedObjectException
	 */
	public SysObject(ObjectMapping mapping) 
			throws MappedObjectException {
		super(mapping);
	}

	public NullableInt getObjectID() {
		return _idObject;
	}

	public void setObjectID(NullableInt _idObject) {
		this._idObject = _idObject;
	}

	public NullableCollatedString getName() {
		return _ncstrName;
	}

	public void setName(NullableCollatedString _ncstrName) {
		this._ncstrName = _ncstrName;
	}

	public NullableInt getSchemaID() {
		return _idSchema;
	}

	public void setSchemaID(NullableInt _idSchema) {
		this._idSchema = _idSchema;
	}

	public NullableInt getParentObjectID() {
		return _idParentObject;
	}

	public void set_ParentObjectID(NullableInt _idParentObject) {
		this._idParentObject = _idParentObject;
	}

	public NullableCollatedString getObjectType() {
		return _ncstrType;
	}

	public void setObjectType(NullableCollatedString _ncstrType) {
		this._ncstrType = _ncstrType;
	}

	public NullableDateTime getCreated() {
		return _ndtCreated;
	}

	public void setCreated(NullableDateTime _ndtCreated) {
		this._ndtCreated = _ndtCreated;
	}

	public NullableDateTime getModified() {
		return _ndtModified;
	}

	public void setModified(NullableDateTime _ndtModified) {
		this._ndtModified = _ndtModified;
	}
	
}
