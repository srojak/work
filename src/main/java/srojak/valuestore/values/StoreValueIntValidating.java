/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.IntPredicate;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public class StoreValueIntValidating
		extends StoreValueIntInstance {
	private final IntPredicate _predValid;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueIntValidating(NamedKey key, int valueInitial, IntPredicate validator) {
		super(key, valueInitial);
		Objects.requireNonNull(validator, "validator");
		_predValid = validator;
		if (!validator.test(valueInitial)) {
			faultInvalid(key, "valueInitial");
		}
	}

	@Override
	protected void validate(int value) {
		if (!_predValid.test(value)) {
			faultInvalid(getKey(), "value");
		}
	}
}
