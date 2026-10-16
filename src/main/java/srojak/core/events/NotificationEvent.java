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
public class NotificationEvent 
		extends CoreEvent {
	private final String _text;

	public NotificationEvent(Object source) {
		super(source);
		_text = "";
	}
	
	public NotificationEvent(Object source, String strText) {
		super(source);
		Objects.requireNonNull(strText, "strText");
		_text = strText;
	}
	
	public String getText() {
		return _text;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		if (!_text.isBlank()) {
			sb.append(", text=\"");
			sb.append(_text);
			sb.append('\"');
		}
	}

}
