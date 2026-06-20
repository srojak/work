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
public class StoreValueObjInstance<T>
		extends StoreValueBase
		implements StoreValueObj<T> {
	public final boolean _bAllowsNull;
	public T _value;
	
	/**
	 * @param key
	 */
	public StoreValueObjInstance(NamedKey key, boolean bAllowsNull, T value) {
		super(key);
		if (!bAllowsNull) {
			Objects.requireNonNull(value, "value");
		}
		_bAllowsNull = bAllowsNull;
		_value = value;
	}

	public T getValue() {
		return _value;
	}
	
	public void setValue(T value) {
		if (!_bAllowsNull) {
			Objects.requireNonNull(value, "value");
		}
		_value = value;
	}
}
