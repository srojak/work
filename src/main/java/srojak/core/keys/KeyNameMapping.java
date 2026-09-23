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
package srojak.core.keys;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class KeyNameMapping<F extends NameEquatableKey, T extends NameEquatableKey> {
	private final F _keyFrom;
	private final T _keyTo;
	
	public KeyNameMapping(F keyFrom, T keyTo) {
		Objects.requireNonNull(keyFrom, "keyFrom");
		Objects.requireNonNull(keyTo, "keyTo");
		_keyFrom = keyFrom;
		_keyTo = keyTo;
	}
	
	public F from() {
		return _keyFrom;
	}
	
	public T to() {
		return _keyTo;
	}
}
