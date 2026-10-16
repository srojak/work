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
import srojak.numerics.vstore.NameKeyedLongCollection;
import srojak.numerics.vstore.StoreValueCalculationBase;
import srojak.numerics.vstore.primitives.StoreValueLong;

@SuppressWarnings("serial")
public class StoreValueLongList
		extends StoreValueList<StoreValueLong>
		implements NameKeyedLongCollection {

	public StoreValueLongList(ClassKey keyStore) {
		super(keyStore);
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
