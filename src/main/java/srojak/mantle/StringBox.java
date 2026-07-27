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
package srojak.mantle;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class StringBox {
	private String _content;
	private boolean _bChanged;
	
	private static final String nullString = "";
	
	public StringBox() {
		_content = nullString;
		_bChanged = false;
	}
	
	public String getContent() {
		return _content;
	}
	
	public void setContent(String strText) {
		Objects.requireNonNull(strText, "strText");
		if (strText.isEmpty())
			throw new IllegalArgumentException("strText is empty");
		_content = strText;
		_bChanged = true;
	}
	
	public void reset() {
		_content = nullString;
		_bChanged = false;
	}
	
	public boolean isChanged() {
		return _bChanged;
	}
	
	public void resetChanged() {
		_bChanged = false;
	}

	@Override
	public String toString() {
		if (_bChanged)
			return _content.toString();
		else
			return "*not set";
	}
}
