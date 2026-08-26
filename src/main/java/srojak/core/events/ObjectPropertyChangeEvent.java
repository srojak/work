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
package srojak.core.events;

import java.util.Objects;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ObjectPropertyChangeEvent 
		extends CoreEvent {
	private final NameToken _nameProperty;
	private final Object _objNewValue;

	/**
	 * @param source
	 */
	public ObjectPropertyChangeEvent(Object source, NameToken nameProperty, Object objNewValue) {
		super(source);
		Objects.requireNonNull(nameProperty, "nameProperty");
		_nameProperty = nameProperty;
		_objNewValue = objNewValue;
	}
	
	public NameToken getProperty() {
		return _nameProperty;
	}
	
	public boolean isPropertyEqual(NameToken token) {
		return token == null ? false : _nameProperty.equals(token);
	}
	
	public Object getNewValue() {
		return _objNewValue;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", property ");
		sb.append(_nameProperty.getName());
		sb.append(", value=");
		if (_objNewValue == null) {
			sb.append("(null)");
		} else {
			sb.append(_objNewValue);
		}
	}

}
