/**
 * 
 */
package srojak.valuestore.values;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueInt;

/**
 * @author Stephen
 *
 */
public class StoreValueIntInstance
		extends StoreValueBase
		implements StoreValueInt {
	private int _nValue;
	
	/**
	 * @param key
	 */
	public StoreValueIntInstance(NamedKey key, int valueInitial) {
		super(key);
		_nValue = valueInitial;
	}
	
	protected void validate(int value) {
		// base class method does nothing
	}

	public int getValue() {
		return _nValue;
	}
	
	public void setValue(int value) {
		validate(value);
		_nValue = value;
	}
}
