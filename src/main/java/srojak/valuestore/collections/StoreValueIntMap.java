/**
 * 
 */
package srojak.valuestore.collections;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreIntCollection;
import srojak.valuestore.StoreValueInt;
import srojak.valuestore.values.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueIntMap
		extends StoreValueMap<StoreValueInt>
		implements GlobalStoreIntCollection {

	public StoreValueIntMap(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueInt get(NamedKey key) {
		return super.get(key);
	}

	@Override
	public void define(StoreValueInt value) {
		Objects.requireNonNull(value, "value");
		super.put(value.getKey(), value);	
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}
}
