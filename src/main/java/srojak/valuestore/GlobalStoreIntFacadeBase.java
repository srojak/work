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
public abstract class GlobalStoreIntFacadeBase
		extends GlobalStoreFacadeBase 
		implements GlobalStoreIntValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreIntFacadeBase(Class<?> classInstance) {
		super(classInstance);
	}

	@Override
	public int getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		StoreValueInt storeValue = this.<GlobalStoreIntCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		return storeValue.getValue();
	}

	@Override
	public int getValueOrDefault(NamedKey key, int valueDefault) {
		StoreValueInt storeValue = this.<GlobalStoreIntCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;		
	}

	@Override
	public void setValue(NamedKey key, int value) {
		Objects.requireNonNull(key, "key");
		StoreValueInt storeValue = this.<GlobalStoreIntCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}

}
