/**
 * 
 */
package srojak.valuestore.collections;

import java.util.HashMap;
import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.StoreKeyNotFoundException;
import srojak.valuestore.StoreValue;
import srojak.valuestore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueMap<V extends StoreValue>
		extends HashMap<NamedKey, V>
		implements StoreValueKeyed {
	private final PackageClassLocator _locator;
	
	public StoreValueMap(PackageClassLocator locator) {
		Objects.requireNonNull(locator, "locator");
		_locator = locator;
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
	public boolean containsKey(NamedKey key) {
		return super.containsKey(key);
	}

	@Override
	public NamedKey[] getAllKeys() {
		NamedKey[] array = new NamedKey[size()];
		int index = 0;
		for (NamedKey key : this.keySet()) {
			array[index++] = key;
		}
		return array;
	}

	@Override
	public boolean canSetValue(NamedKey key) {
		V entry = super.get(key);
		if (entry == null) {
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		}
		return entry.canSet();
	}
}
