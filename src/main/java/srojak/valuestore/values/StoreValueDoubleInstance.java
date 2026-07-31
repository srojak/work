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
package srojak.valuestore.values;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueDouble;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleInstance 
		extends StoreValueScalarBase 
		implements StoreValueDouble {
	private double _dValue;

	/**
	 * @param key
	 */
	public StoreValueDoubleInstance(NamedKey key, double valueInitial) {
		super(key);
		_dValue = valueInitial;
	}
	
	protected void validate(double value) {
		// base class method does nothing
	}

	@Override
	public double getValue() {
		return _dValue;
	}
	
	@Override
	public void setValue(double value) {
		validate(value);
		_dValue = value;
	}
}
