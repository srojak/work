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
package srojak.core.keys;

import java.util.Arrays;
import java.util.Objects;

import srojak.core.ClassInstanceNumber;
import srojak.core.InstanceNumber;

/**
 * @author Stephen
 *
 */
public class ClassInstanceNumberKey 
		extends AbstractInstanceNumberKey
		implements ClassInstanceNumber {
	private final Class<?> _owner;
	
	public ClassInstanceNumberKey(Class<?> classOwner, int number) {
		super(number);
		Objects.requireNonNull(classOwner, "classOwner");
		_owner = classOwner;
	}

	@Override
	public Class<?> owningClass() {
		return _owner;
	}

	@Override
	public boolean isEqual(ClassInstanceNumber obj) {
		if (obj != null) {
			return _owner == obj.owningClass()
					&& _instance == obj.number();
		}
		return false;
	}

	@Override
	public boolean isNumericallyEqual(int nValue) {
		return _instance == nValue;
	}

	@Override
	public InstanceNumber getAbstractKey() {
		return new InstanceNumberKey(_instance);
	}

	@Override
	protected String getLabel() {
		return _owner.getSimpleName();
	}

	@Override
	public int hashCode() {
		// want to avoid the boxing
		int[] values = new int[] { _owner.hashCode(), _instance };
		return Arrays.hashCode(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof ClassInstanceNumber other) {
			return isEqual(other);
		} else if (obj instanceof InstanceNumber other) {
			return isNumericallyEqual(other);
		} else {
			return false;
		}
	}
}
