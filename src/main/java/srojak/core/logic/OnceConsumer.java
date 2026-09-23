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
package srojak.core.logic;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class OnceConsumer<T> 
		extends OnceBase {
	private final Consumer<T> _consumer;

	public OnceConsumer(Consumer<T> consumer, boolean bIsReady) {
		super(bIsReady);
		Objects.requireNonNull(consumer, "consumer");
		_consumer = consumer;
	}

	public void execute(T arg) {
		Objects.requireNonNull(arg, "arg");
		faultIfNotReady();
		if (!isDone()) {
			_consumer.accept(arg);
			markDone();
		}
	}
}
