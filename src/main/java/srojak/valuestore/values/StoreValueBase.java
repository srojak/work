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
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValue;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueBase
		implements StoreValue {
	private final NamedKey _key;
	
	public StoreValueBase(NamedKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
	}

	@Override
	public String getName() {
		return _key.getName();
	}

	@Override
	public NamedKey getKey() {
		return _key;
	}
	
	@Override
	public boolean canSet() {
		return true;
	}

	@Override
	public StoreValueCalculationBase getCalculation() {
		return null;
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _key.equals(obj);
	}

	protected static void faultInvalid(NamedKey key, String strValueName) {
		throw new IllegalArgumentException("invalid " + strValueName + " for " + key);
	}
}
