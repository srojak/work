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
public abstract class GlobalStoreObjFacadeBase<V>
		extends GlobalStoreFacadeBase
		implements GlobalStoreObjValueFacade<V> {

	/**
	 * @param classInstance
	 */
	public GlobalStoreObjFacadeBase(Class<?> classInstance) {
		super(classInstance);
	}

	@Override
	public V getValue(NamedKey key) {
		StoreValueObj<V> storeValue = this.<GlobalStoreObjCollection<V>>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : null;
	}

	@Override
	public V getValueOrDefault(NamedKey key, V valueDefault) {
		StoreValueObj<V> storeValue = this.<GlobalStoreObjCollection<V>>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;
	}

	@Override
	public void setValue(NamedKey key, V value) {
		Objects.requireNonNull(key, "key");
		StoreValueObj<V> storeValue = this.<GlobalStoreObjCollection<V>>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}

}
