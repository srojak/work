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
public class StoreValueIntList 
		extends StoreValueList<StoreValueInt> 
		implements GlobalStoreIntCollection {

	/**
	 * @param locator
	 */
	public StoreValueIntList(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueInt get(NamedKey key) {
		return super.findByKey(key);
	}

	@Override
	public void define(StoreValueInt value) {
		Objects.requireNonNull(value, "value");
		super.add(value);
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}
}
