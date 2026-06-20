/**
 * 
 */
package srojak.valuestore.collections;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreObjCollection;
import srojak.valuestore.StoreValueObj;
import srojak.valuestore.values.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueObjMap<V>
		extends StoreValueMap<StoreValueObj<V>> 
		implements GlobalStoreObjCollection<V> {

	public StoreValueObjMap(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueObj<V> get(NamedKey key) {
		return super.get(key);
	}

	@Override
	public void define(StoreValueObj<V> value) {
		Objects.requireNonNull(value, "value");
		super.put(value.getKey(), value);	
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}

}
