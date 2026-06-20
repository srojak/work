/**
 * 
 */
package srojak.valuestore;

import java.util.Objects;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class GlobalStoreDoubleFacadeBase
		extends GlobalStoreFacadeBase
		implements GlobalStoreDoubleValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreDoubleFacadeBase(Class<?> classInstance) {
		super(classInstance);
	}

	@Override
	public double getValue(NamedKey key) {
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : Double.NaN;
	}

	@Override
	public double getValueOrDefault(NamedKey key, double valueDefault) {
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;
	}

	@Override
	public void setValue(NamedKey key, double value) {
		Objects.requireNonNull(key, "key");
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}

}
