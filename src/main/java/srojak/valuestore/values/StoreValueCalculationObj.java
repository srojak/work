/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.Function;

import srojak.core.keys.NamedKey;
import srojak.valuestore.GlobalStoreObjCollection;

/**
 * @author Stephen
 *
 */
public class StoreValueCalculationObj<V>
		extends StoreValueCalculationBindable<GlobalStoreObjCollection<V>> {
	private final Function<GlobalStoreObjCollection<V>, V> _fnCalc;

	public StoreValueCalculationObj(Function<GlobalStoreObjCollection<V>, V> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		super(dependentCar, dependentCdr);
		Objects.requireNonNull(calculation, "calculation");
		_fnCalc = calculation;
	}

	public V calculate() {
		return _fnCalc.apply(getBoundCollection());
	}
}
