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
package srojak.mantle;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class Receptor<T>
		implements ReceptorReceiver<T> {
	private Class<T> _class;
	private T _value;
	
	public Receptor(Class<T> classValue) {
		Objects.requireNonNull(classValue, "classValue");
		_class = classValue;
		_value = null;
	}

	@Override
	public Class<?> getObjectClass() {
		return _class;
	}

	@Override
	public boolean hasBeenSet() {
		return _value != null;
	}

	@Override
	public void require() {
		if (_value == null) {
			throw new IllegalStateException("receptor for class "
					+ _class.getSimpleName() + " has not been set");
		}		
	}

	protected void faultIfAlreadySet() {
		if (_value != null) {
			throw new IllegalStateException("receptor has already been set");
		}
	}
	
	protected void afterReceiving(T value) {
		// base class method does nothing
	}
	
	public T get() {
		if (_value == null) {
			throw new IllegalStateException("receptor has never been set");
		}
		return _value;
	}
	
	@Override
	public void receive(T value) {
		Objects.requireNonNull(value, "value");
		faultIfAlreadySet();
		_value = value;
		afterReceiving(_value);
	}
}
