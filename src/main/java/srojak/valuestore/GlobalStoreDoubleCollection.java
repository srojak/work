/**
 * 
 */
package srojak.valuestore;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 * the interface that a global store of {@code double} values must provide.
 */
public interface GlobalStoreDoubleCollection
		extends StoreValueKeyed {
	
	/**
	 * Get the value store for a key.
	 * @param key The key for the value.
	 * @return the store for the value, or {@code null} if not defined.
	 */
	StoreValueDouble get(NamedKey key);
	
	/**
	 * Define a value in the store.
	 * @param value The store for the value.
	 */
	void define(StoreValueDouble value);
}
