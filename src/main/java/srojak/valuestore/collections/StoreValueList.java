/**
 * 
 */
package srojak.valuestore.collections;

import java.util.ArrayList;
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
public class StoreValueList<V extends StoreValue> 
		extends ArrayList<V>
		implements StoreValueKeyed {
	private final PackageClassLocator _locator;
	
	public StoreValueList(PackageClassLocator locator) {
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
	
	protected V findByKey(NamedKey key) {
		for (V item : this) {
			if (item.getKey().equals(key)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public boolean containsKey(NamedKey key) {
		return findByKey(key) != null;
	}

	@Override
	public NamedKey[] getAllKeys() {
		NamedKey[] array = new NamedKey[size()];
		int index = 0;
		for (V item : this) {
			array[index++] = item.getKey();
		}
		return array;
	}

	@Override
	public boolean canSetValue(NamedKey key) {
		V entry = findByKey(key);
		if (entry == null) {
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		}
		return entry.canSet();
	}

}
