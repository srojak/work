/**
 * Copyright © 2026 Stephen Rojak.
 * 
 * This file is part of the srojak Java portfolio.
 * 
 * The srojak Java portfolio is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 3 of the License.
 * 
 * The srojak Java portfolio is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this portfolio.
 * If not, see <https://www.gnu.org/licenses/>.
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
public class StoreValueObjList<V> 
		extends StoreValueList<StoreValueObj<V>> 
		implements GlobalStoreObjCollection<V> {

	public StoreValueObjList(PackageClassLocator locator) {
		super(locator);
	}

	@Override
	public StoreValueObj<V> get(NamedKey key) {
		return super.findByKey(key);
	}

	@Override
	public void define(StoreValueObj<V> value) {
		Objects.requireNonNull(value, "value");
		super.add(value);
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}

}
