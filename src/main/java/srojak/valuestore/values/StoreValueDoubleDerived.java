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

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueDouble;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleDerived
		extends StoreValueDerivedBase
		implements StoreValueDouble {
	private final StoreValueCalculationDouble _calc;

	/**
	 * @param key
	 * @param valuesDependentOn
	 */
	public StoreValueDoubleDerived(NamedKey key, StoreValueCalculationDouble calculation ) {
		super(key);
		Objects.requireNonNull(calculation, "calculation");
		_calc = calculation;
	}

	@Override
	public StoreValueCalculationBase getCalculation() {
		return _calc;
	}

	@Override
	public double getValue() {
		return _calc.calculate();
	}

	@Override
	public void setValue(double value) {
		throw new UnsupportedOperationException("cannot set value for " + getKey().getName());	
	}

}
