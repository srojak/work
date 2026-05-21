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
package srojak.core;

/**
 * @author Stephen Rojak
 *
 */
public abstract class Counter<T extends Comparable<T>> {
	protected T _value;
	
	public Counter(T value) {
		_value = value;
	}
	
	public T getValue() {
		return _value;
	}
	
	public void setValue(T newValue) {
		_value = newValue;
	}
	
	public int compareTo(T value) {
		return _value.compareTo(value);
	}
	
	public abstract void increment(T incrValue);
	
	public abstract void decrement(T decrValue);
}
