/**
 * 
 */
package srojak.valuestore;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 * The interface that a global store facade holding  {@code Object} data must provide.
 * @param <V> the type of object to be stored in the global store.
 */
public interface GlobalStoreFacade<V>
		extends GlobalStoreCommon {
	
	/**
	 * Get the value for the key.
	 * @param key The key identifying the value.
	 * @return the value associated with the key, or {@code null} if the key is not defined.
	 */
	V getValue(NamedKey key);
	
	/**
	 * Get the value for the key, or a default value if the key is not defined.
	 * @param key The key identifying the value.
	 * @param valueDefault The value to return if the key is not defined.
	 * @return The value associated with the key, if any, or the default value otherwise.
	 */
	V getValueOrDefault(NamedKey key, V valueDefault);
	
	/**
	 * Sets the value for the key.
	 * @param key The key identifying the value.
	 * @param value The value to set.
	 * @throws StoreKeyNotFoundException if the key is not defined.
	 * @throws IllegalArgumentException if the value for the key has validations
	 *   and these validation requirements were not met.
	 */
	void setValue(NamedKey key, V value);
}
