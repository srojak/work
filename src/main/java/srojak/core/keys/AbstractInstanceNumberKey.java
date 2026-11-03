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

import srojak.core.InstanceNumberCommon;

/**
 * @author Stephen
 *
 */
public abstract class AbstractInstanceNumberKey 
		implements InstanceNumberCommon {
	protected final int _instance;
	
	protected AbstractInstanceNumberKey(int number) {
		_instance = number;		
	}

	@Override
	public boolean isExcluded() {
		return false;
	}

	@Override
	public int number() {
		return _instance;
	}

	@Override
	public boolean isNumericallyEqual(int nValue) {
		return _instance == nValue;
	}
	
	@Override
	public boolean isNumericallyEqual(InstanceNumberCommon other) {
		return _instance == other.number();
	}

	protected abstract String getLabel();
	
	@Override
	public String toString() {
		return String.valueOf(_instance);
	}
	
	@Override
	public String toDetailedString() {
		return getLabel() + "[" + String.valueOf(_instance) + "]";
	}
}
