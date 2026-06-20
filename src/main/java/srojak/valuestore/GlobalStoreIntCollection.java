/**
 * 
 */
package srojak.valuestore;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 * The interface that a global store facade holding {@code int} data must provide.
 */
public interface GlobalStoreIntCollection 
		extends StoreValueKeyed {
	
	/**
	 * Get the value store for a key.
	 * @param key The key for the value.
	 * @return the store for the value, or {@code null} if not defined.
	 */
	StoreValueInt get(NamedKey key);
	
	/**
	 * Define a value in the store.
	 * @param value The store for the value.
	 */
	void define(StoreValueInt value);
}
