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
package srojak.xml.stream;

import javax.xml.stream.XMLStreamReader;

/**
 * @author Stephen
 *
 */
public class XmlPendingTextCollector {
	private StringBuilder _sb;
	private boolean _bSeenFirstChar;
	private boolean _bIgnoreInitialWhiteSpace;
	
	public XmlPendingTextCollector() {
		_sb = new StringBuilder();
		_bSeenFirstChar = false;
	}
	
	public boolean ignoreInitialWhiteSpace() {
		return _bIgnoreInitialWhiteSpace;
	}
	
	public void setIgnoreInitialWhiteSpace(boolean bState) {
		_bIgnoreInitialWhiteSpace = bState;
	}
	
	public boolean isEmpty() {
		return _sb.isEmpty();
	}
	
	private void clearContent() {
		_sb = new StringBuilder();
		_bSeenFirstChar = false;
	}
	
	private String captureContent() {
		String strContent = _sb.toString();
		clearContent();
		return strContent;
	}
	
	public void reset() {
		clearContent();
	}
	
	public void acceptChars(XMLStreamReader reader) {
		char[] array = reader.getTextCharacters();
		int nStart = reader.getTextStart();
		for (int index = 0; index < reader.getTextLength(); index++) {
			char c = array[nStart + index];
			if (_bSeenFirstChar) {
				_sb.append(c);
			} else {
				if (Character.isWhitespace(c)) {
					if (!_bIgnoreInitialWhiteSpace) {
						_sb.append(c);
					}
				} else {
					_bSeenFirstChar = true;
					_sb.append(c);
				}
			}
		}
	}
	
	public void acceptCData(XMLStreamReader reader) {
		int n = reader.getTextLength();
		_sb.append(reader.getTextCharacters(), reader.getTextStart(), n);
		if (n > 0) {
			_bSeenFirstChar = true;
		}
	}
	
	public String getContent() {
		return captureContent();
	}
}
