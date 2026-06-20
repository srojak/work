/**
 * 
 */
package srojak.valuestore;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public abstract class GlobalStoreLongFacadeBase extends GlobalStoreFacadeBase
		implements GlobalStoreLongValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreLongFacadeBase(Class<?> classInstance) {
		super(classInstance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		return storeValue.getValue();
	}

	@Override
	public long getValueOrDefault(NamedKey key, long valueDefault) {
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;		
	}

	@Override
	public void setValue(NamedKey key, long value) {
		Objects.requireNonNull(key, "key");
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}

	@Override
	protected StoreValueKeyed initializeStore(PackageClassLocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

}
