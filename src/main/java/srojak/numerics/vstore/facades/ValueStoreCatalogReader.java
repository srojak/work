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

import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiConsumer;

import srojak.core.keys.ClassKey;
import srojak.core.observe.ObservationCollector;
import srojak.numerics.vstore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
public class ValueStoreCatalogReader {
	
	public ValueStoreCatalogReader() {
		
	}

	
	/**
	 * Gets the current observation collector in use for the global stores.
	 * @return the current observation collector.
	 */
	public ObservationCollector getObservationWriter() {
		return FacadeCatalog.getObservationWriter();
	}
	
	/**
	 * Sets the current observation collector in use for the global stores.
	 * @param writer the observation collector to use.
	 * @throws NullPointerException if {@code collector} is {@code null}.
	 */
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		FacadeCatalog.setObservationWriter(collector);
	}
	
	/**
	 * Gets the size of the global store collection.
	 * @return The number of global stores in the collection.
	 */
	public int storeSize() {
		return FacadeCatalog.size();
	}
	
	/**
	 * Does a key for a global store exist in the collection?
	 * @param locator The key for which to test.
	 * @return {@code true} if the key exists in the store.
	 */
	public boolean containsKey(ClassKey key) {
		Objects.requireNonNull(key, "key");
		return FacadeCatalog.containsKey(key);
	}
	
	/**
	 * Get a global store by its key.
	 * @param locator The key for the global store to retrieve.
	 * @return The global store for the key, or {@code null} if there is no store for the key.
	 */
	public StoreValueKeyed getStore(ClassKey key) {
		Objects.requireNonNull(key, "key");
		return FacadeCatalog.getStore(key);
	}
	
	/**
	 * Iterate over the global store collection, performing an action on each entry.
	 * @param action The action to be performed for each entry.
     * @throws NullPointerException if the specified action is null
     * @throws ConcurrentModificationException if an entry is found to be
     *   removed during iteration
     * @see java.util.Map<K, V>#forEach(BiConsumer<? super K, ? super V> action)
	 */
	public void forEach(BiConsumer<ClassKey, ? super StoreValueKeyed> action) {
		Objects.requireNonNull(action, "action");
		FacadeCatalog.forEach(action);
	}
}
