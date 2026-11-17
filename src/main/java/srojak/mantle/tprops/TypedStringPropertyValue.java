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
package srojak.mantle.tprops;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import srojak.core.Optionality;
import srojak.mantle.text.StringRestriction;

/**
 * @author Stephen
 *
 */
public class TypedStringPropertyValue 
		extends AbstractTypedStringProperty {
	private final List<StringRestriction> _listRestrictions;

	/**
	 * @param keyClass
	 * @param strName
	 * @param opt
	 */
	public TypedStringPropertyValue(String strName, Optionality opt, StringRestriction ... restrictions) {
		super(strName, opt);
		Objects.requireNonNull(restrictions, "restrictions");
		_listRestrictions = Arrays.asList(restrictions);
	}
	
	TypedStringPropertyValue(String strName, Optionality opt, List<StringRestriction> listRestrictions) {
		super(strName, opt);
		Objects.requireNonNull(listRestrictions, "listRestrictions");
		_listRestrictions = List.copyOf(listRestrictions);
	}
	
	public void setDefaultValue(String value) {
		Objects.requireNonNull(value, "value");
		for (StringRestriction restr : _listRestrictions) {
			if (!restr.validate(value)) {
				throw new IllegalArgumentException("value: did not meet " + restr.getName());
			}
		}
		innerSetDefaultValue(value);
	}

	@Override
	public void setValue(String value) {
		Objects.requireNonNull(value, "value");
		for (StringRestriction restr : _listRestrictions) {
			if (!restr.validate(value)) {
				throw new IllegalArgumentException("value: did not meet " + restr.getName());
			}
		}
		innerSetValue(value);
	}

	@Override
	public TypedProperty copy() {
		TypedStringPropertyValue copy = new TypedStringPropertyValue(getName(), Optionality.OPTIONAL, _listRestrictions);
		copy.copyValue(value());
		copy.copyFlagsFrom(this);
		return copy;
	}
}
