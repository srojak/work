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
package srojak.core.observe;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * @author Stephen
 *
 */
public class StackCapture {
	private final StackTraceElement[] _stack;

	/**
	 * 
	 */
	public StackCapture() {
		_stack = Thread.currentThread().getStackTrace();
	}
	
	public StackCapture(int nSkipInitial) {
		if (nSkipInitial < 0) {
			throw new IllegalArgumentException("nSkipInitial cannot be negative");
		}
		StackTraceElement[] source = Thread.currentThread().getStackTrace();
		_stack = Arrays.copyOfRange(source, nSkipInitial, source.length);
	}
	
	public void walk(Consumer<StackTraceElement> consumer) {
		Objects.requireNonNull(consumer);
		for (StackTraceElement e : _stack) {
			consumer.accept(e);
		}
	}
	
	public void walkNumbered(ObjIntConsumer<StackTraceElement> consumer) {
		Objects.requireNonNull(consumer);
		int nDepth = 0;
		for (StackTraceElement e : _stack) {
			consumer.accept(e, nDepth++);
		}
	}

}
