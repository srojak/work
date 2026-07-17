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
package srojak.core.collections;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.NameToken;
import srojak.core.NameTokenEquatable;

/**
 * @author Stephen
 *
 */
public class NameTokenKeyedMap<E extends NameTokenEquatable> 
		implements NameTokenKeyedMapReadOnly<E> {
	private final HashMap<NameToken, E> _map;
	
	public NameTokenKeyedMap() {
		_map = new HashMap<NameToken, E>();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public boolean containsKey(NameToken key) {
		return _map.containsKey(key);
	}

	@Override
	public E get(NameToken key) {
		return _map.get(key);
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		_map.values().forEach(consumer);	
	}
	
	public void add(E value) {
		Objects.requireNonNull(value, "value");
		_map.put(value.getNameToken(), value);
	}

	public E remove(NameToken key) {
		return _map.remove(key);
	}
}
