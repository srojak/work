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
import srojak.numerics.function.ToFloatFunction;
import srojak.valuestore.GlobalStoreFloatCollection;

/**
 * @author Stephen
 *
 */
public class StoreValueCalculationFloat
		extends StoreValueCalculationBindable<GlobalStoreFloatCollection> {
	private final ToFloatFunction<GlobalStoreFloatCollection> _fnCalc;

	/**
	 * 
	 */
	public StoreValueCalculationFloat(ToFloatFunction<GlobalStoreFloatCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		super(dependentCar, dependentCdr);
		Objects.requireNonNull(calculation, "calculation");
		_fnCalc = calculation;
	}
	
	public float calculate() {
		return _fnCalc.applyAsFloat(getBoundCollection());
	}

}
