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

import srojak.core.ClassInstanceNumber;
import srojak.core.InstanceNumber;

/**
 * @author Stephen
 *
 */
public class InstanceNumberKey 
		extends AbstractInstanceNumberKey 
		implements InstanceNumber {
	
	private static final String LABEL = "instance";

	/**
	 * @param number
	 */
	public InstanceNumberKey(int number) {
		super(number);
	}

	@Override
	public boolean isEqual(InstanceNumber obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!obj.isExcluded()) {
			return _instance == obj.number();
		} else {
			return false;
		}
	}

	@Override
	public boolean isComprehensivelyEqual(InstanceNumber obj) {
		return isEqual(obj);
	}

	@Override
	public InstanceNumber getAbstractKey() {
		return this;
	}

	@Override
	protected String getLabel() {
		return LABEL;
	}

	@Override
	public int hashCode() {
		// this is what Integer.hashCode does anyway
		return _instance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof InstanceNumber other) {
			return isEqual(other);
		} else if (obj instanceof ClassInstanceNumber other) {
			return isNumericallyEqual(other);
		} else {
			return false;
		}
	}

}
