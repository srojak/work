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
package srojak.core.reflect;

import java.util.Objects;

/**
 * @author Stephen
 *
 * A value along with its type, to overcome type erasure issues.
 * @param <V> the base type of value that the object can store.
 */
public class TypeBearingValue<V> {
	private final Class<?> _classValue;
	protected final V _value;
	
	/**
	 * Constructor.
	 * @param value The value to store.
	 */
	public TypeBearingValue(V value)
	{
		Objects.requireNonNull(value, "value");
		_classValue = value.getClass();
		_value = value;
	}
	
	public Class<?> getValueClass() {
		return _classValue;
	}
	
	public boolean isValueOfType(Class<?> classFor)
	{
		Objects.requireNonNull(classFor, "classFor");
		return classFor.isAssignableFrom(_classValue);
	}
	
	public V getValue() {
		return _value;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends V> T getValueAs(Class<T> classReturn) {
		Objects.requireNonNull(classReturn, "classReturn");
		if (classReturn.isAssignableFrom(_classValue)) {
			return (T) _value;			
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _value.equals(obj);
	}
	
	public boolean valueEquals(V other) {
		return _value.equals(other);
	}
}
