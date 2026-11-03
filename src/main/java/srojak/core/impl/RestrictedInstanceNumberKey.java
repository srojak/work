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
package srojak.core.impl;

import java.util.Objects;

import srojak.core.InstanceNumber;
import srojak.core.InstanceNumberCommon;
import srojak.core.keys.AbstractInstanceNumberKey;

/**
 * @author Stephen
 *
 */
public class RestrictedInstanceNumberKey 
		extends AbstractInstanceNumberKey 
		implements InstanceNumber {
	private final String _name;
	
	public RestrictedInstanceNumberKey(String strName) {
		super(-1);
		Objects.requireNonNull(strName, "strName");
		_name = strName;
	}

	@Override
	public boolean isExcluded() {
		return true;
	}

	@Override
	public boolean isNumericallyEqual(int nValue) {
		return false;
	}

	@Override
	public boolean isNumericallyEqual(InstanceNumberCommon other) {
		return false;
	}

	@Override
	public boolean isEqual(InstanceNumber obj) {
		return false;
	}

	@Override
	public boolean isComprehensivelyEqual(InstanceNumber obj) {
		if (obj instanceof RestrictedInstanceNumberKey other) {
			return _name.equals(other._name);
		}
		return false;
	}

	@Override
	public InstanceNumber getAbstractKey() {
		return this;
	}

	@Override
	protected String getLabel() {
		return _name;
	}

	@Override
	public String toString() {
		return "(" + _name + ")";
	}

	@Override
	public String toDetailedString() {
		return "instance[" + _name + "]";
	}

}
