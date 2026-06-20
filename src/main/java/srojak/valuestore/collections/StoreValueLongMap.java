/**
 * 
 */
package srojak.valuestore.collections;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreLongCollection;
import srojak.valuestore.StoreValueLong;
import srojak.valuestore.values.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueLongMap
		extends StoreValueMap<StoreValueLong>
		implements GlobalStoreLongCollection {

	public StoreValueLongMap(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueLong get(NamedKey key) {
		return super.get(key);
	}

	@Override
	public void define(StoreValueLong value) {
		Objects.requireNonNull(value, "value");
		super.put(value.getKey(), value);	
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}

}
