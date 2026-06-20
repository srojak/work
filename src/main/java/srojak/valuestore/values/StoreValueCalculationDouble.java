/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.ToDoubleFunction;

import srojak.core.keys.NamedKey;
import srojak.valuestore.GlobalStoreDoubleCollection;

/**
 * @author Stephen
 *
 */
public class StoreValueCalculationDouble
		extends StoreValueCalculationBindable<GlobalStoreDoubleCollection> {
	private final ToDoubleFunction<GlobalStoreDoubleCollection> _fnCalc;

	/**
	 * 
	 */
	public StoreValueCalculationDouble(ToDoubleFunction<GlobalStoreDoubleCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		super(dependentCar, dependentCdr);
		Objects.requireNonNull(calculation, "calculation");
		_fnCalc = calculation;
	}
	
	public double calculate() {
		return _fnCalc.applyAsDouble(getBoundCollection());
	}
}
