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

import srojak.core.InputLocation;

/**
 * @author Stephen
 *
 */
public class InputLocationContainer 
		implements InputLocation {
	private final int _nLine;
	private final int _nColumn;
	
	public InputLocationContainer(int nLine, int nColumn) {
		_nLine = nLine;
		_nColumn = nColumn;
	}

	@Override
	public int getLineNumber() {
		return _nLine;
	}

	@Override
	public int getColumnNumber() {
		return _nColumn;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[line=");
		if (_nLine < 0) {
			sb.append('?');
		} else {
			sb.append(_nLine);
		}
		sb.append(", col=");
		if (_nColumn < 0) {
			sb.append('?');
		} else {
			sb.append(_nColumn);
		}
		sb.append(']');
		return sb.toString();
	}
}
