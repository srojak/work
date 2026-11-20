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

import srojak.collate.Collation;
import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableValue;
import srojak.sql.NullableCollatedString;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.MappedObjectTextProperty;
import srojak.sql.valbind.JdbcValueBinding;
import srojak.sql.valbind.NullableCollatedLatin1StringBinding;

/**
 * @author Stephen
 *
 */
public class MappedLatin1StringProperty 
		extends AbstractMappedTextProperty
		implements MappedObjectTextProperty {

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 * @param sqlType
	 * @param maxLength
	 * @param collation
	 */
	public MappedLatin1StringProperty(Field field, String strColumnName, FlagsIntTest flagsSource, 
			int sqlType, short maxLength, Collation collation) {
		super(field, strColumnName, flagsSource, sqlType, maxLength, collation);
	}

	@Override
	protected NullableValue readFrom(ResultSet rset) 
			throws SQLException {
		String strContent = null;
		if (isUnicode()) {
			strContent = rset.getNString(_nameColumn);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(_collation);
			} else {
				if (trimAfterReading()) {
					strContent = strContent.trim();
				}
				return NullableCollatedString.from(_collation, strContent);
			}
		} else {
			strContent = rset.getString(_nameColumn);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(_collation);
			} else {
				if (trimAfterReading()) {
					strContent = strContent.trim();
				}
				return NullableCollatedString.from(_collation, strContent);
			}
		}
	}

	@Override
	public JdbcValueBinding bind(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException, ValueBindingException {
		NullableCollatedString nble = getFieldValueAs(instance);
		checkNullState(nble);
		return new NullableCollatedLatin1StringBinding(_nameColumn, _sqlType, nble, _maxLength);
	}
}
