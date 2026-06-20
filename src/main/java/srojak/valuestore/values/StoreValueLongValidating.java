/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.LongPredicate;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public class StoreValueLongValidating
		extends StoreValueLongInstance {
	private final LongPredicate _predValid;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueLongValidating(NamedKey key, long valueInitial, LongPredicate validator) {
		super(key, valueInitial);
		Objects.requireNonNull(validator, "validator");
		_predValid = validator;
		if (!validator.test(valueInitial)) {
			faultInvalid(key, "valueInitial");
		}
	}

	@Override
	protected void validate(long value) {
		if (!_predValid.test(value)) {
			faultInvalid(getKey(), "value");
		}
	}

}
