/**
 * 
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
