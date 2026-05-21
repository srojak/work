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
 * @author Stephen
 *
 * an integer counter object that can be used by lambdas.
 */
public class IntegerCounter {
	private int _value;

	/**
	 * Value constructor.
	 * @param value The initial value of the counter.
	 */
	public IntegerCounter(int value) {
		_value = value;
	}
	
	/**
	 * Default constructor.
	 */
	public IntegerCounter() {
		this(0);
	}
	
	/**
	 * Gets the current value of the counter.
	 * @return The current value of the counter.
	 */
	public int getValue() {
		return _value;
	}
	
	/**
	 * Increment the counter by one.
	 */
	public void increment() {
		_value++;
	}

	/**
	 * Increment the counter by a given amount.
	 * @param incrValue The amount to increment the counter.
	 * @throws IllegalArgumentException if the argument is negative.
	 */
	public void increment(int incrValue) {
		if (incrValue < 0) 
			throw new IllegalArgumentException("incrValue is negative");
		_value += incrValue;		
	}
	
	/**
	 * Decrement the counter by one.
	 */
	public void decrement() {
		_value--;
	}

	/**
	 * Decrement the counter by a given amount.
	 * @param decrValue The amount to decrement the counter.
	 * @throws IllegalArgumentException if the argument is negative.
	 */
	public void decrement(int decrValue) {
		if (decrValue < 0) 
			throw new IllegalArgumentException("decrValue is negative");
		_value -= decrValue;
	}

	/**
	 * Returns a string representation of the object.
	 * @return The string representation of the current value of the counter.
	 */
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
}
