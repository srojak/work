/**
 * 
 */
package srojak.valuestore;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
@GlobalStore
public abstract class GlobalStoreFacadeBase
		implements GlobalStoreCommon {
	private final PackageClassLocator _locator;
	private final StoreValueKeyed _store;
	
	public GlobalStoreFacadeBase(Class<?> classInstance) {
		_locator = new PackageClassLocator(classInstance);
		_store = SingletonStore.getOrCreateStore(_locator, () -> {
			return initializeStore(_locator);
			
		});
	}

	@Override
	public PackageClassLocator getClassLocator() {
		return _locator;
	}

	@Override
	public String getLocatorName() {
		return _locator.getFullName();
	}

	@Override
	public boolean isEmpty() {
		return _store.isEmpty();
	}

	@Override
	public int size() {
		return _store.size();
	}

	@Override
	public boolean containsKey(NamedKey key) {
		return _store.containsKey(key);
	}
	
	@SuppressWarnings("unchecked")
	protected <S extends StoreValueKeyed> S getStoreAs() {
		return (S) _store;
	}

	protected abstract StoreValueKeyed initializeStore(PackageClassLocator locator);

	@Override
	public boolean canSetValue(NamedKey key) {
		return _store.canSetValue(key);
	}
}
