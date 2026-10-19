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
package srojak.numerics.vstore;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.collections.NameKeyedDoubleCollection;
import srojak.numerics.vstore.collections.NameKeyedIntCollection;
import srojak.numerics.vstore.collections.NameKeyedLongCollection;
import srojak.numerics.vstore.collections.NameKeyedObjCollection;
import srojak.numerics.vstore.primitives.StoreValueCalculationDouble;
import srojak.numerics.vstore.primitives.StoreValueCalculationInt;
import srojak.numerics.vstore.primitives.StoreValueCalculationLong;

/**
 * @author Stephen
 *
 */
public class Calculations {

	public static StoreValueCalculationDouble makeDouble(
			ToDoubleFunction<NameKeyedDoubleCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationDouble(calculation, dependentCar, dependentCdr);
	}
	
	public static StoreValueCalculationInt makeInt(
			ToIntFunction<NameKeyedIntCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationInt(calculation, dependentCar, dependentCdr);
	}
	
	public static StoreValueCalculationLong makeLong(
			ToLongFunction<NameKeyedLongCollection> calculation,
			NamedKey dependentCar, NamedKey[] dependentCdr) {
		return new StoreValueCalculationLong(calculation, dependentCar, dependentCdr);
	}

	public static <V> StoreValueCalculationObj<V> makeObj(
			Function<NameKeyedObjCollection<V>, V> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationObj<V>(calculation, dependentCar, dependentCdr);
	}
}
