package srojak.valuestore;

import srojak.core.keys.NamedKey;

/**
 *
 * The interface that a global store facade holding {@code long} data must provide.
 * 
 * @author Stephen
 *
 */
public interface GlobalStoreLongCollection 
		extends StoreValueKeyed {
	
	/**
	 * Get the value store for a key.
	 * @param key The key for the value.
	 * @return the store for the value, or {@code null} if not defined.
	 */
	StoreValueLong get(NamedKey key);
	
	/**
	 * Define a value in the store.
	 * @param value The store for the value.
	 */
	void define(StoreValueLong value);

}
