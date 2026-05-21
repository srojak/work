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
package srojak.core.containers;

import java.util.LinkedList;
import java.util.function.Consumer;

import srojak.core.NotifyingValue;
/**
 * @author Stephen
 *
 */
public class NotifyingValueContainer<T>
		implements NotifyingValue<T> {
	private T _value;
	private LinkedList<Consumer<T>> _consumers;
	
	public NotifyingValueContainer(T value) {
		if (value == null) {
			throw new IllegalArgumentException("value");
		}
		_value = value;
		_consumers = new LinkedList<Consumer<T>>();
	}
	
	@Override
	public T getValue() {
		return _value;
	}
	
	public void syncConsumers() {
		for (Consumer<T> consumer : _consumers) {
			consumer.accept(_value);
		}
	}
	
	public void setValue(T newValue) {
		if (newValue == null) {
			throw new IllegalArgumentException("newValue");
		}
		_value = newValue;
		syncConsumers();
	}

	@Override
	public void addConsumer(Consumer<T> consumer) {
		if (consumer == null) {
			throw new IllegalArgumentException("consumer");
		}
		_consumers.add(consumer);
	};

	@Override
	public void addConsumerAndSync(Consumer<T> consumer) {
		if (consumer == null) {
			throw new IllegalArgumentException("consumer");
		}
		_consumers.add(consumer);
		consumer.accept(_value);
	}

	@Override
	public boolean removeConsumer(Consumer<T> consumer) {
		return _consumers.remove(consumer);
	}
}
