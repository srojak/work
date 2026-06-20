/**
 * 
 */
package srojak.valuestore.values;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import srojak.core.keys.NamedKey;
import srojak.valuestore.GlobalStoreDoubleCollection;
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
