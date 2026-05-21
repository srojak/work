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
package srojak.xml.stream.impl;

import javax.xml.stream.Location;

/**
 * @author Stephen
 *
 */
public class XmlSourceLocation
		implements Location {
	private final String _strIdPublic;
	private final String _strIdSystem;
	private final int _nLine;
	private final int _nColumn;
	private final int _nOffset;
	
	public static final XmlSourceLocation NULL = new XmlSourceLocation();
	
	private XmlSourceLocation() {
		_strIdPublic = null;
		_strIdSystem = null;
		_nLine = -1;
		_nColumn = -1;
		_nOffset = -1;
	}
	
	public XmlSourceLocation(String strPublicId, String strSystemId, 
			int nLine, int nColumn, int nOffset) {
		_strIdPublic = strPublicId;
		_strIdSystem = strSystemId;
		_nLine = nLine;
		_nColumn = nColumn;
		_nOffset = nOffset;
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
	public int getCharacterOffset() {
		return _nOffset;
	}

	@Override
	public String getPublicId() {
		return _strIdPublic;
	}

	@Override
	public String getSystemId() {
		return _strIdSystem;
	}

}
