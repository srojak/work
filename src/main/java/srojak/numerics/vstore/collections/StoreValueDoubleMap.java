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
import srojak.numerics.vstore.NameKeyedDoubleCollection;
import srojak.numerics.vstore.StoreValueCalculationBase;
import srojak.numerics.vstore.StoreValueDouble;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueDoubleMap 
		extends StoreValueMap<StoreValueDouble>
		implements NameKeyedDoubleCollection {

	public StoreValueDoubleMap(ClassKey keyStore) {
		super(keyStore);
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
