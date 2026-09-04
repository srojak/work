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
package srojak.mantle.quants;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class TextBlockSizeQty
		implements TextBlockSize {
	private final int _lines;
	private final int _columns;
	
	public TextBlockSizeQty(int nLines, int nColumns) {
		if (nLines <= 0) {
			throw new IllegalArgumentException("nLines must be positive");
		}
		if (nColumns <= 0) {
			throw new IllegalArgumentException("nColumns must be positive");
		}
		_lines = nLines;
		_columns = nColumns;
	}

	@Override
	public int lines() {
		return _lines;
	}

	@Override
	public int columns() {
		return _columns;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_columns, _lines);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof TextBlockSize other) {
			return _lines == other.lines() && _columns == other.columns();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("_lines=");
		builder.append(_lines);
		builder.append(", _columns=");
		builder.append(_columns);
		return builder.toString();
	}
}
