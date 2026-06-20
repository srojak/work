/**
 * 
 */
package srojak.valuestore.values;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueLong;

/**
 * @author Stephen
 *
 */
public class StoreValueLongInstance 
		extends StoreValueBase 
		implements StoreValueLong {
	private long _lnValue;

	/**
	 * @param key
	 */
	public StoreValueLongInstance(NamedKey key, long valueInitial) {
		super(key);
		_lnValue = valueInitial;
	}
	
	protected void validate(long value) {
		// base class method does nothing
	}

	@Override
	public long getValue() {
		return _lnValue;
	}

	@Override
	public void setValue(long value) {
		validate(value);
		_lnValue = value;
	}

}
