/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.ToLongFunction;

import srojak.core.keys.NamedKey;
import srojak.valuestore.GlobalStoreLongCollection;

/**
 * @author Stephen
 *
 */
public class StoreValueCalculationLong
		extends StoreValueCalculationBindable<GlobalStoreLongCollection> {
	private final ToLongFunction<GlobalStoreLongCollection> _fnCalc;

	public StoreValueCalculationLong(ToLongFunction<GlobalStoreLongCollection> calculation,
			NamedKey dependentCar, NamedKey[] dependentCdr) {
		super(dependentCar, dependentCdr);
		Objects.requireNonNull(calculation, "calculation");
		_fnCalc = calculation;
	}

	public long calculate() {
		return _fnCalc.applyAsLong(getBoundCollection());
	}
}
