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
package srojak.sql.mapobj.factories;

import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.NullableCollatedString;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.props.AbstractMappedTextProperty;
import srojak.sql.mapobj.props.MappedEncodedStringProperty;
import srojak.sql.mapobj.props.MappedLatin1StringProperty;

/**
 * @author Stephen
 *
 */
public final class NullableCollatedStringPropertyFactory 
		extends AbstractTextPropertyFactory {

	public NullableCollatedStringPropertyFactory() {
		super(NullableCollatedString.class);
	}

	@Override
	public MappedObjectProperty create(ClassKey keyForObject) throws MappedObjectException {
		int sqlType = deriveSqlTypeFromFlags();
		AbstractMappedTextProperty property = null;
		if (_collation.isCharsetLatin1()) {
			property = new MappedLatin1StringProperty(_field, _nameColumn, _flags, sqlType, _maxLength, _collation);
		} else {
			property = new MappedEncodedStringProperty(_field, _nameColumn, _flags, sqlType, _maxLength, _collation);
		}
		applyKeyTo(property);
		return property;
	}

}
