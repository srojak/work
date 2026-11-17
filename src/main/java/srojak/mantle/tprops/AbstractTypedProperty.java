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
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public abstract class AbstractTypedProperty 
		implements TypedProperty {
	private final String _name;
	private final ClassKey _keyClass;
	private final FlagsShort _flags;
	
	public AbstractTypedProperty(ClassKey keyClass, String strName, Optionality opt) {
		Objects.requireNonNull(keyClass, "keyClass");
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		Objects.requireNonNull(opt, "opt");
		_name = strName;
		_keyClass = keyClass;
		_flags = new FlagsShort();
		if (opt.isRequired()) {
			_flags.set(FLAGS_IS_REQUIRED);
		}
	}

	@Override
	public boolean isNameEqual(String strName) {
		return _name.equals(strName);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public ClassKey propertyClass() {
		return _keyClass;
	}

	@Override
	public FlagsShortTest flags() {
		return _flags;
	}

	@Override
	public boolean isRequired() {
		return _flags.test(FLAGS_IS_REQUIRED);
	}

	@Override
	public boolean isSet() {
		return _flags.test(FLAGS_IS_SET);
	}
	
	@Override
	public boolean allowsNull() {
		return _flags.test(FLAGS_ALLOWS_NULL);
	}

	protected void valueIsSet() {
		_flags.set(FLAGS_IS_SET);
	}
	
	protected void setAllowsNullFlag() {
		_flags.set(FLAGS_ALLOWS_NULL);
	}
	
	protected boolean isBooleanValueSet() {
		return _flags.test(FLAGS_BOOL_VALUE);
	}
	
	protected void setBooleanValue(boolean bState) {
		_flags.apply(bState, FLAGS_BOOL_VALUE);
	}
	
	@Override
	public abstract String valueAsString();

	@Override
	public abstract TypedProperty copy();
	
	protected void copyFlagsFrom(AbstractTypedProperty propertySource) {
		_flags.copyFrom(propertySource._flags);
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof TypedProperty other) {
			return _name.equals(other.getName());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "name=" + valueAsString();
	}
	
	
}
