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

import java.io.StringWriter;
import java.util.Objects;

import srojak.core.text.TextBufferSegment;

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
	
	private void addCharFiltered(char c) {
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
	
	public void acceptChars(StringWriter carrier) {
		StringBuffer buf = carrier.getBuffer();
		for (int index = 0; index < buf.length(); index++) {
			char c = buf.charAt(index);
			addCharFiltered(c);
		}
	}
	
	public void acceptChars(TextBufferSegment segment) {
		Objects.requireNonNull(segment, "segment");
		for (int index = 0; index < segment.getLength(); index++) {
			char c = segment.charAtOffset(index);
			addCharFiltered(c);
		}
	}
	
	public void acceptCData(StringWriter carrier) {
		int lengthBefore = _sb.length();
		_sb.append(carrier.toString());
		if (_sb.length() > lengthBefore) {
			_bSeenFirstChar = true;
		}
	}
	
	public void acceptCData(TextBufferSegment segment) {
		Objects.requireNonNull(segment, "segment");
		for (int index = 0; index < segment.getLength(); index++) {
			char c = segment.charAtOffset(index);
			addCharFiltered(c);
		}
		if (segment.getLength() > 0) {
			_bSeenFirstChar = true;
		}
	}
	
	public String getContent() {
		return captureContent();
	}
}
