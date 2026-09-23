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

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class MapChangeVerbEvent
		extends ListChangeVerbEvent {
	private final Object _objKey;

	/**
	 * @param source
	 * @param verb
	 */
	public MapChangeVerbEvent(Object source, int verb) {
		super(source, verb);
		_objKey = null;
	}

	/**
	 * @param source
	 * @param verb
	 * @param item
	 */
	public MapChangeVerbEvent(Object source, int verb, Object key, Object item) {
		super(source, verb, item);
		_objKey = key;
	}

	public Object getKey() {
		return _objKey;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		formatVerb(sb);
		if (_objKey != null) {
			sb.append(", key=[");
			sb.append(_objKey);
			sb.append(']');
		}
		formatItem(sb);
	}
}
