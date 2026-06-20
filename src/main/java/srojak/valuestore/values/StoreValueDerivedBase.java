/**
 * 
 */
package srojak.valuestore.values;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueDerivedBase
		extends StoreValueBase {

	protected StoreValueDerivedBase(NamedKey key) {
		super(key);
	}
	
	@Override
	public boolean canSet() {
		return false;
	}

	@Override
	public abstract StoreValueCalculationBase getCalculation();
}
