/**
 * 
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
