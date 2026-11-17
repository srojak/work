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

import java.util.Objects;

import srojak.core.Optionality;
import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class TypedClassPropertyValue
		extends AbstractTypedProperty 
		implements TypedClassProperty {
	private Class<?> _classRef;
	
	private static final ClassKey CLASS_KEY = new ClassKey(Class.class);

	/**
	 * @param keyClass
	 * @param strName
	 * @param opt
	 */
	public TypedClassPropertyValue(String strName, Optionality opt) {
		super(CLASS_KEY, strName, opt);
		_classRef = null;
	}

	@Override
	public Class<?> value() {
		return _classRef;
	}

	@Override
	public void setValue(Class<?> classRef) {
		Objects.requireNonNull(classRef, "classRef");
		_classRef = classRef;
	}

	@Override
	public TypedProperty copy() {
		TypedClassPropertyValue copy = new TypedClassPropertyValue(getName(), Optionality.OPTIONAL);
		if (_classRef != null) {
			setValue(_classRef);
		}
		copy.copyFlagsFrom(this);
		return copy;
	}

	@Override
	public String valueAsString() {
		if (_classRef == null) {
			return "";
		} else {
			return _classRef.getName();
		}
	}

}
