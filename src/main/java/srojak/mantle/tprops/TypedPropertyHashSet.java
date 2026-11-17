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
package srojak.mantle.tprops;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.excepts.IncommensurableValuesException;
import srojak.core.excepts.KeyNotFoundException;

/**
 * @author Stephen
 *
 */
public class TypedPropertyHashSet 
		implements TypedPropertySet {
	private final Map<String, TypedProperty> _map;
	
	public TypedPropertyHashSet() {
		_map = new HashMap<String, TypedProperty>();
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
	public boolean containsKey(String strName) {
		return _map.containsKey(strName);
	}

	@Override
	public TypedProperty get(String strName) {
		return _map.get(strName);
	}

	@Override
	public <P extends TypedProperty> P getAs(String strName, Class<?> classProperty) {
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(classProperty, "classProperty");
		TypedProperty prop = _map.get(strName);
		if (prop == null) {
			throw new KeyNotFoundException(strName, "not defined");
		}
		if (!classProperty.isAssignableFrom(prop.getClass())) {
			throw new IncommensurableValuesException(classProperty, prop.getClass(), "class not conforming");
		}
		@SuppressWarnings("unchecked")
		P property = (P) prop;
		return property;
	}

	@Override
	public Stream<TypedProperty> stream() {
		return _map.values().stream();
	}
	
	public boolean put(TypedProperty property) {
		Objects.requireNonNull(property, "property");
		return _map.put(property.getName(), property) == null;
	}

	public boolean putIfAbsent(TypedProperty property) {
		Objects.requireNonNull(property, "property");
		return _map.putIfAbsent(property.getName(), property) == null;
	}
}
