/**
 * 
 */
package srojak.valuestore.values;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueDouble;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleInstance 
		extends StoreValueBase 
		implements StoreValueDouble {
	private double _dValue;

	/**
	 * @param key
	 */
	public StoreValueDoubleInstance(NamedKey key, double valueInitial) {
		super(key);
		_dValue = valueInitial;
	}
	
	protected void validate(double value) {
		// base class method does nothing
	}

	@Override
	public double getValue() {
		return _dValue;
	}
	
	@Override
	public void setValue(double value) {
		validate(value);
		_dValue = value;
	}
}
