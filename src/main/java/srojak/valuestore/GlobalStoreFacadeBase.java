/**
 * Copyright © 2026 Stephen Rojak.
 * 
 * This file is part of the srojak Java portfolio.
 * 
 * The srojak Java portfolio is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 3 of the License.
 * 
 * The srojak Java portfolio is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this portfolio.
 * If not, see <https://www.gnu.org/licenses/>.
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
