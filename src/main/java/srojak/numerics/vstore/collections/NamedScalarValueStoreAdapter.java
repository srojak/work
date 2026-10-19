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

import java.util.Map;
import java.util.Objects;

import srojak.core.excepts.InitializationRuntimeException;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreScalarValue;
import srojak.numerics.vstore.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 */
public class NamedScalarValueStoreAdapter<S extends StoreScalarValue>
		extends NamedValueStoreAdapter<S> {

	/**
	 * @param classStore
	 * @param classElements
	 * @param storeBase
	 */
	public NamedScalarValueStoreAdapter(Class<?> classStore, Class<?> classElements, Map<NamedKey, S> storeBase) {
		super(classStore, classElements, storeBase);
	}

	public void define(S value) {
		Objects.requireNonNull(value, "value");
		NamedKey key = value.getKey();
		if (putIfAbsent(key, value) != null) {
			throw new InitializationRuntimeException(getStoreKey().getReferencedClass(),
					key.getName() + " is already defined");
		}
		StoreValueCalculationBase calc = value.getCalculation();
		if (calc != null) {
			calc.bindTo(this);
		}
	}
}
