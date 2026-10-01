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
package srojak.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import srojak.core.TextMessageRelay;

/**
 * @author Stephen
 *
 */
public class TextRelayTextPrinter 
		extends LineBasedTextPrinterBase {
	private final TextMessageRelay _relay;

	/**
	 * 
	 */
	public TextRelayTextPrinter(TextMessageRelay relay) {
		super();
		Objects.requireNonNull(relay, "relay");
		_relay = relay;
		setReady();
	}

	@Override
	protected void writeFromBuffer(ByteArrayOutputStream buffer) throws IOException {
		_relay.writeln(buffer.toString());
	}

	@Override
	protected void innerClose() throws IOException {
		// nothing to do
		
	}

}
