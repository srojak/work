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
public class StoreValueDoubleList 
		extends StoreValueList<StoreValueDouble> 
		implements GlobalStoreDoubleCollection {

	public StoreValueDoubleList(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueDouble get(NamedKey key) {
		return super.findByKey(key);
	}

	@Override
	public void define(StoreValueDouble value) {
		Objects.requireNonNull(value, "value");
		super.add(value);
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}

}
