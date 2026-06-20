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

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class ClassBearingCoreEvent
		extends CoreEvent {
	protected final Class<?> _classObj;

	public ClassBearingCoreEvent(Object source, Class<?> classObj) {
		super(source);
		Objects.requireNonNull(classObj, "classObj");
		_classObj = classObj;
	}
	
	public ClassBearingCoreEvent(Object source, Object objValue) {
		super(source);
		Objects.requireNonNull(objValue, "objValue");
		_classObj = objValue.getClass();
	}
	
	public Class<?> getValueClass() {
		return _classObj;
	}
	
	public abstract boolean isValueNull();

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append("value(class=");
		sb.append(_classObj.getSimpleName());
	}
}
