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
package srojak.core.text;

import java.io.PrintStream;
import java.util.Objects;

import srojak.core.logic.BooleanFlag;

/**
 * @author Stephen
 *
 */
public class PrintStreamTextReceiver 
		implements TextReceiver {
	private final PrintStream _stream;
	private final BooleanFlag _bFaulted;

	public PrintStreamTextReceiver(PrintStream stream) {
		Objects.requireNonNull(stream, "stream");
		_stream = stream;
		_bFaulted = new BooleanFlag(false);
	}

	@Override
	public boolean isOpen() {
		return !_bFaulted.getState();
	}

	@Override
	public void receive(String strText, boolean bImpliedNewline) {
		if (!_bFaulted.getState()) {
			_stream.print(strText);
			if (bImpliedNewline) {
				_stream.println();
			}
			if (_stream.checkError()) {
				_bFaulted.set();
			}
		}

	}

	@Override
	public boolean isAutoFlush() {
		return true;
	}

	@Override
	public void flush() {
		_stream.flush();
	}

}
