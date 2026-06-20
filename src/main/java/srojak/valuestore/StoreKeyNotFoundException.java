/**
 * 
 */
package srojak.valuestore;

import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public class StoreKeyNotFoundException
		extends RuntimeException {
	private final PackageClassLocator _locator;

	/**
	 * @param message
	 */
	public StoreKeyNotFoundException(PackageClassLocator locator, String message) {
		super(message);
		_locator = locator;
	}
	
	public PackageClassLocator getClassLocator() {
		return _locator;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4600062079431588752L;

}
