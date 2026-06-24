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

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import srojak.core.keys.NamedKey;
import srojak.numerics.function.ToFloatFunction;
import srojak.valuestore.GlobalStoreDoubleCollection;
import srojak.valuestore.GlobalStoreFloatCollection;
import srojak.valuestore.GlobalStoreIntCollection;
import srojak.valuestore.GlobalStoreLongCollection;
import srojak.valuestore.GlobalStoreObjCollection;

/**
 * @author Stephen
 *
 */
public class Calculations {

	public static StoreValueCalculationDouble makeDouble(
			ToDoubleFunction<GlobalStoreDoubleCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationDouble(calculation, dependentCar, dependentCdr);
	}
	
	public static StoreValueCalculationFloat makeFloat(
			ToFloatFunction<GlobalStoreFloatCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationFloat(calculation, dependentCar, dependentCdr);
	}
	
	public static StoreValueCalculationInt makeInt(
			ToIntFunction<GlobalStoreIntCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationInt(calculation, dependentCar, dependentCdr);
	}
	
	public static StoreValueCalculationLong makeLong(
			ToLongFunction<GlobalStoreLongCollection> calculation,
			NamedKey dependentCar, NamedKey[] dependentCdr) {
		return new StoreValueCalculationLong(calculation, dependentCar, dependentCdr);
	}

	public static <V> StoreValueCalculationObj<V> makeObj(
			Function<GlobalStoreObjCollection<V>, V> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		return new StoreValueCalculationObj<V>(calculation, dependentCar, dependentCdr);
	}
}
