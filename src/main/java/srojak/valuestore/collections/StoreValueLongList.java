package srojak.valuestore.collections;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreLongCollection;
import srojak.valuestore.StoreValueLong;
import srojak.valuestore.values.StoreValueCalculationBase;

@SuppressWarnings("serial")
public class StoreValueLongList
		extends StoreValueList<StoreValueLong>
		implements GlobalStoreLongCollection {

	public StoreValueLongList(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueLong get(NamedKey key) {
		return super.findByKey(key);
	}

	@Override
	public void define(StoreValueLong value) {
		Objects.requireNonNull(value, "value");
		super.add(value);
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}
}
