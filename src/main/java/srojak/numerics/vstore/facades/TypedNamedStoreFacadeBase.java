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
package srojak.numerics.vstore.facades;

import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreValueKeyed;
import srojak.numerics.vstore.TypedNamedKeyedCollection;

/**
 * @author Stephen
 *
 */
public abstract class TypedNamedStoreFacadeBase
		implements TypedNamedKeyedCollection {

	protected TypedNamedStoreFacadeBase() {
		
	}
	
	protected abstract StoreValueKeyed store();

	@Override
	public ClassKey getClassKey() {
		return store().getStoreKey();
	}

	@Override
	public boolean isEmpty() {
		return store().isEmpty();
	}

	@Override
	public int size() {
		return store().size();
	}

	@Override
	public boolean containsKey(NamedKey key) {
		return store().containsKey(key);
	}

	@Override
	public boolean canSetValue(NamedKey key) {
		return store().canSetValue(key);
	}
	
	protected abstract void initializeSelf();
	
	protected final void registerSelf() {
		StoreValueKeyed store = store();
		ClassKey keyStore = store.getStoreKey();
		if (FacadeCatalog.registerStore(keyStore, store)) {
			initializeSelf();
		}
	}
}
