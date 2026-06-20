/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValue;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueBase
		implements StoreValue {
	private final NamedKey _key;
	
	public StoreValueBase(NamedKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
	}

	@Override
	public String getName() {
		return _key.getName();
	}

	@Override
	public NamedKey getKey() {
		return _key;
	}
	
	@Override
	public boolean canSet() {
		return true;
	}

	@Override
	public StoreValueCalculationBase getCalculation() {
		return null;
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _key.equals(obj);
	}

	protected static void faultInvalid(NamedKey key, String strValueName) {
		throw new IllegalArgumentException("invalid " + strValueName + " for " + key);
	}
}
