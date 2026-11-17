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
package srojak.sql.mapobj.props;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableValue;
import srojak.numerics.nullable.NullableDateTime;
import srojak.sql.SQLCommon;
import srojak.sql.mapobj.MappedObjectTimeZonedProperty;

/**
 * @author Stephen
 *
 */
public class MappedDateTimeProperty
		extends AbstractMappedObjectProperty
		implements MappedObjectTimeZonedProperty {
	private ZoneId _idZone;

	/**
	 * @param field
	 * @param strColumnName
	 */
	public MappedDateTimeProperty(Field field, String strColumnName, FlagsIntTest flagsSource) {
		super(field, strColumnName, flagsSource, Types.TIMESTAMP);
		_idZone = SQLCommon.getServerZone();
	}

	@Override
	public void setTimeZone(ZoneId idZone) {
		Objects.requireNonNull(idZone, "idZone");
		_idZone = idZone;
	}

	@Override
	public ZoneId timeZone() {
		return _idZone;
	}

	@Override
	protected NullableValue readFrom(ResultSet rset)
			throws SQLException {
		LocalDateTime dtIn = rset.getObject(_nameColumn, LocalDateTime.class);
		if (rset.wasNull()) {
			return NullableDateTime.fromNull();
		} else {
			ZonedDateTime zdtValue = dtIn.atZone(_idZone);
			return NullableDateTime.from(zdtValue);
		}
	}
}
