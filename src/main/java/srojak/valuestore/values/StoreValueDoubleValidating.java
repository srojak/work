/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.DoublePredicate;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleValidating 
		extends StoreValueDoubleInstance {
	private final DoublePredicate _predValid;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueDoubleValidating(NamedKey key, double valueInitial,
			DoublePredicate validator) {
		super(key, valueInitial);
		Objects.requireNonNull(validator, "validator");
		_predValid = validator;
		if (!validator.test(valueInitial)) {
			faultInvalid(key, "valueInitial");
		}
	}

	@Override
	protected void validate(double value) {
		if (!_predValid.test(value)) {
			faultInvalid(getKey(), "value");
		}
	}
}
