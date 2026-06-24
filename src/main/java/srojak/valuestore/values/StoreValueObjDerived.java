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
import srojak.valuestore.StoreValueObj;

/**
 * @author Stephen
 *
 */
public class StoreValueObjDerived<T>
		extends StoreValueDerivedBase 
		implements StoreValueObj<T> {
	private final StoreValueCalculationObj<T> _calc;
	
	/**
	 * @param key
	 */
	public StoreValueObjDerived(NamedKey key, StoreValueCalculationObj<T> calculation) {
		super(key);
		Objects.requireNonNull(calculation, "calculation");
		_calc = calculation;
	}

	@Override
	public StoreValueCalculationBase getCalculation() {
		return _calc;
	}

	@Override
	public T getValue() {
		return _calc.calculate();
	}

	@Override
	public void setValue(T value) {
		throw new UnsupportedOperationException("cannot set value for " + getKey().getName());	
	}

}
