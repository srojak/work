/**
 * 
 */
package srojak.valuestore;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * The interface that all stores of any type must provide to be accepted into the global store.
 */
public interface StoreValueKeyed {
	
	/**
	 * Gets the key for the global store.
	 * @return the {@code PackageClassLocator} of the originating facade.
	 */
	PackageClassLocator getClassLocator();
	
	/**
	 * Gets the name of the {@code PackageClassLocator} key.
	 * @return the {@code String} name of the key.
	 */
	String getLocatorName();
	
	/**
	 * Is the store empty?
	 * @return {@code true} if empty.
	 */
	boolean isEmpty();
	
	/**
	 * Get the number of items in the store.
	 * @return the number of items in the store.
	 */
	int size();
	
	/**
	 * Test for the existence of the key in the store.
	 * @param key The key for which to test.
	 * @return {@code true} if the key exists in the store.
	 */
	boolean containsKey(NamedKey key);
	
	/**
	 * Get all the keys in the store
	 * @return an array of all the keys currently in the store.
	 */
	NamedKey[] getAllKeys();
	
	/**
	 * Can the value associated with this key be changed directly?
	 * @param key The key for which to test.
	 * @return {@code true} if the key can be changed.
	 */
	boolean canSetValue(NamedKey key);
}
