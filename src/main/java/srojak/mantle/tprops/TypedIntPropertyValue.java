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

import srojak.core.Optionality;
import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class TypedIntPropertyValue 
		extends AbstractTypedProperty 
		implements TypedIntProperty {
	private int _value;
	
	private static final ClassKey CLASS_KEY = new ClassKey(int.class);

	/**
	 * @param strName
	 * @param opt
	 */
	public TypedIntPropertyValue(String strName, Optionality opt) {
		super(CLASS_KEY, strName, opt);
		_value = 0;
	}

	@Override
	public int value() {
		return _value;
	}
	
	protected void setInitialValue(int value) {
		_value = value;
	}

	@Override
	public void setValue(int value) {
		_value = value;
		super.valueIsSet();
	}

	@Override
	public TypedProperty copy() {
		TypedIntPropertyValue copy = new TypedIntPropertyValue(getName(), Optionality.OPTIONAL);
		copy.setInitialValue(_value);
		copy.copyFlagsFrom(this);
		return copy;
	}

	@Override
	public String valueAsString() {
		return String.valueOf(_value);
	}
}
