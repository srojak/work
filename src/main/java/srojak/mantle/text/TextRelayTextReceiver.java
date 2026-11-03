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
package srojak.mantle.text;

import java.util.Objects;

import srojak.core.TextMessageRelay;

/**
 * @author Stephen
 *
 */
public class TextRelayTextReceiver 
		implements TextReceiver {
	private final TextMessageRelay _relay;
	
	public TextRelayTextReceiver(TextMessageRelay relay) {
		Objects.requireNonNull(relay, "relay");
		_relay = relay;
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void receive(String strText, boolean bImpliedNewline) {
		_relay.writeln(strText);
	}

	@Override
	public boolean isAutoFlush() {
		return true;
	}

	@Override
	public void flush() {
		// no effect
	}

}
