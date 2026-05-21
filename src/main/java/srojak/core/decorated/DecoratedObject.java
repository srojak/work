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
package srojak.core.decorated;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratedObject<T>
		implements Decorated<T> {
	private final T _value;
	private final Map<NameToken, Decorator> _decorators;
	
	public DecoratedObject(T value) {
		Objects.requireNonNull(value, "value");
		_value = value;
		_decorators = new HashMap<NameToken, Decorator>();
	}

	@Override
	public T getValue() {
		return _value;
	}

	@Override
	public boolean bHasDecorator(NameToken tokenKey) {
		return _decorators.containsKey(tokenKey);
	}

	@Override
	public Decorator getDecorator(NameToken tokenKey) {
		return _decorators.get(tokenKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Decorator> D getDecoratorAs(NameToken tokenKey) {
		Decorator db = _decorators.get(tokenKey);
		if (db == null) {
			return null;
		} else {
			return (D) db;
		}
	}

	@Override
	public void putDecorator(Decorator decorator) {
		_decorators.put(decorator.getNameToken(), decorator);		
	}

	@Override
	public boolean isEqualTo(T other) {
		return other != null ? _value.equals(other) : false;
	}

	@Override
	public boolean isEqualTo(Decorated<T> other) {
		return other != null ? _value.equals(other.getValue()) : false;
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof Decorated other) {
			return _value.equals(other.getValue());
		} else {
			return _value.equals(obj);
		}
	}

	@Override
	public String toString() {
		return _value.toString();
	}
}
