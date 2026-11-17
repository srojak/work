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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import srojak.core.Optionality;

/**
 * @author Stephen
 *
 */
public class TypedStringChoicePropertyValue 
		extends AbstractTypedStringProperty 
		implements TypedStringChoiceProperty {
	private final List<String> _listChoices;

	/**
	 * @param strName
	 * @param opt
	 */
	public TypedStringChoicePropertyValue(String strName, Optionality opt, Collection<String> choices) {
		super(strName, opt);
		Objects.requireNonNull(choices, "choices");
		_listChoices = List.copyOf(choices);
	}

	@Override
	public void setValue(String value) {
		Objects.requireNonNull(value, "value");
		if (!_listChoices.contains(value)) {
			throw new IllegalArgumentException("value is not among allowable choices");
		}
		innerSetValue(value);
	}

	@Override
	public List<String> choices() {
		return _listChoices;
	}

	@Override
	public TypedProperty copy() {
		TypedStringChoicePropertyValue copy = new TypedStringChoicePropertyValue(getName(), Optionality.OPTIONAL, _listChoices);
		copy.copyValue(value());
		copy.copyFlagsFrom(this);
		return copy;
	}

}
