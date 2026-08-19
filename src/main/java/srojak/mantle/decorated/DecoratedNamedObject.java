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
package srojak.mantle.decorated;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenEquatable;

/**
 * @author Stephen
 *
 */
public class DecoratedNamedObject<T>
		extends DecoratedObject<T>
		implements DecoratedNamed<T> {
	private final NameToken _tokenKey;
	
	public DecoratedNamedObject(NameToken tokenKey, T value) {
		super(value);
		Objects.requireNonNull(tokenKey, "tokenKey");
		if (tokenKey.isRestricted()) {
			throw new IllegalArgumentException("tokenKey is restricted");
		}
		_tokenKey = tokenKey;
	}

	@Override
	public NameToken getNameToken() {
		return _tokenKey;
	}
	
	@Override
	public boolean isNameTokenEqual(NameToken token) {
		if (token == null) {
			return false;
		} else {
			return _tokenKey.equals(token);
		}
	}

	@Override
	public int hashCode() {
		return _tokenKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof NameToken other) {
			return _tokenKey.equals(other);
		} else if (obj instanceof NameTokenEquatable other) {
			return other.isNameTokenEqual(_tokenKey);
		} else {
			return false;
		}
	}

}
