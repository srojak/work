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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratorsMap implements Decorators {
		private final Map<NameToken, Decorator> _mapDecorators;
		
	public DecoratorsMap() {
		_mapDecorators = new HashMap<NameToken, Decorator>();
	}

	@Override
	public boolean bHasDecorator(NameToken tokenKey) {
		return _mapDecorators.containsKey(tokenKey);
	}

	@Override
	public Decorator getDecorator(NameToken tokenKey) {
		return _mapDecorators.get(tokenKey);
	}

	@Override
	public <D extends Decorator> D getDecoratorAs(NameToken tokenKey) {
		Decorator db = _mapDecorators.get(tokenKey);
		if (db == null) {
			return null;
		} else {
			@SuppressWarnings("unchecked")
			D dbx = (D) db;
			return dbx;
		}
	}

	@Override
	public void putDecorator(Decorator decorator) {
		Objects.requireNonNull(decorator, "decorator");
		_mapDecorators.put(decorator.getNameToken(), decorator);		
	}

	@Override
	public boolean putDecoratorIfAbsent(Decorator decorator) {
		Objects.requireNonNull(decorator, "decorator");
		return _mapDecorators.putIfAbsent(decorator.getNameToken(), decorator) == null;
	}
}
