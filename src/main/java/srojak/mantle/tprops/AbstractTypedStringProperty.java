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
public abstract class AbstractTypedStringProperty 
		extends AbstractTypedProperty implements TypedStringProperty {
	private String _value;
	
	private static final ClassKey CLASS_KEY = new ClassKey(String.class);

	/**
	 * @param strName
	 * @param opt
	 */
	public AbstractTypedStringProperty(String strName, Optionality opt) {
		super(CLASS_KEY, strName, opt);
		_value = "";
	}
	
	protected void copyValue(String value) {
		_value = value;
	}

	@Override
	public String value() {
		return _value;
	}
	
	protected final void innerSetDefaultValue(String value) {
		_value = value;
	}
	
	protected final void innerSetValue(String value) {
		_value = value;
		super.valueIsSet();
	}

	@Override
	public String valueAsString() {
		return _value;
	}

}
