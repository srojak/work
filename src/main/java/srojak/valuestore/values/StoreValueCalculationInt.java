/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.ToIntFunction;

import srojak.core.keys.NamedKey;
import srojak.valuestore.GlobalStoreIntCollection;

/**
 * @author Stephen
 *
 */
public class StoreValueCalculationInt
		extends StoreValueCalculationBindable<GlobalStoreIntCollection> {	
	private final ToIntFunction<GlobalStoreIntCollection> _fnCalc;

	public StoreValueCalculationInt(ToIntFunction<GlobalStoreIntCollection> calculation,
			NamedKey dependentCar, NamedKey ... dependentCdr) {
		super(dependentCar, dependentCdr);
		Objects.requireNonNull(calculation, "calculation");
		_fnCalc = calculation;
	}

	public int calculate() {
		return _fnCalc.applyAsInt(getBoundCollection());
	}
}
