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
package srojak.numerics.vstore.collections;

import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.NameKeyedIntCollection;
import srojak.numerics.vstore.StoreValueCalculationBase;
import srojak.numerics.vstore.StoreValueInt;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueIntList 
		extends StoreValueList<StoreValueInt> 
		implements NameKeyedIntCollection {

	/**
	 * @param locator
	 */
	public StoreValueIntList(ClassKey keyStore) {
		super(keyStore);
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
