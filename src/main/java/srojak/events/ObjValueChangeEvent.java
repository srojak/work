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
package srojak.events;

/**
 * @author Stephen
 *
 */
public class ObjValueChangeEvent<T>
		extends ClassBearingCoreEvent {
	private final T _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5896283193041673115L;
	
	public ObjValueChangeEvent(Object source, Class<?> classObj) {
		super(source, classObj);
		_value = null;
	}
	
	/**
	 * @param source
	 * @param objValue
	 */
	public ObjValueChangeEvent(Object source, T objValue) {
		super(source, objValue);
		_value = objValue;
	}

	@Override
	public boolean isValueNull() {
		return _value == null;
	}
	
	public T getValue() {
		return _value;
	}
}
