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
package srojak.utest.swing;

import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
import srojak.events.IntValueChangeEvent;
import srojak.events.IntValueChangeListener;
import srojak.gui.SliderPromptInteger;

/**
 * @author Stephen
 *
 */
public class SliderValueChangeListener
		implements IntValueChangeListener {
	private final TextMessageRelay _messageOut;
	
	public SliderValueChangeListener(TextMessageRelay msgOut) {
		_messageOut = msgOut;
	}

	@Override
	public void update(IntValueChangeEvent event) {
		StringBuilder sb = new StringBuilder();
		Object objSource = event.getSource();
		if (objSource instanceof SliderPromptInteger sp) {
			NameToken token = sp.getNameTag();
			sb.append("slider ");
			sb.append(token);
			sb.append(" value ");
			sb.append(event.getValue());
			_messageOut.writeln(sb.toString());
		}
	}

}
