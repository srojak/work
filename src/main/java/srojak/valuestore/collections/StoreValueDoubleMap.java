/**
 * 
 */
package srojak.valuestore.collections;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreDoubleCollection;
import srojak.valuestore.StoreValueDouble;
import srojak.valuestore.values.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueDoubleMap 
		extends StoreValueMap<StoreValueDouble>
		implements GlobalStoreDoubleCollection {

	public StoreValueDoubleMap(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueDouble get(NamedKey key) {
		return super.get(key);
	}

	@Override
	public void define(StoreValueDouble value) {
		Objects.requireNonNull(value, "value");
		super.put(value.getKey(), value);	
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}

}
