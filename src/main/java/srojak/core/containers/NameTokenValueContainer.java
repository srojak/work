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
package srojak.core.containers;

import srojak.core.NameToken;
import srojak.core.NameTokenBearing;

/**
 * @author Stephen
 *
 */
public class NameTokenValueContainer<V>
		extends KeyValueContainer<NameToken, V> 
		implements NameTokenBearing {

	/**
	 * @param key
	 * @param value
	 */
	public NameTokenValueContainer(NameToken key, V value) {
		super(key, value);
		if (key.isRestricted()) {
			throw new IllegalArgumentException("key is restricted");
		}
	}

	@Override
	public NameToken getNameToken() {
		return getKey();
	}

	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return token == null ? false : getKey().equals(token);
	}

}
