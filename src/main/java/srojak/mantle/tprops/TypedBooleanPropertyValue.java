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
public final class TypedBooleanPropertyValue 
		extends AbstractTypedProperty 
		implements TypedBooleanProperty {
	
	private static final ClassKey CLASS_KEY = new ClassKey(boolean.class);

	/**
	 * @param strName
	 * @param opt
	 */
	public TypedBooleanPropertyValue(String strName, Optionality opt, boolean bInitialState) {
		super(CLASS_KEY, strName, opt);
		super.setBooleanValue(bInitialState);
	}

	@Override
	public boolean isTrue() {
		return super.isBooleanValueSet();
	}

	@Override
	public void setValue(boolean bState) {
		super.setBooleanValue(bState);
		super.valueIsSet();
	}

	@Override
	public TypedProperty copy() {
		TypedBooleanPropertyValue copy = new TypedBooleanPropertyValue(getName(), Optionality.OPTIONAL, isTrue());
		copy.copyFlagsFrom(this);
		return copy;
	}

	@Override
	public String valueAsString() {
		return super.isBooleanValueSet() ? "true" : "false";
	}

}
