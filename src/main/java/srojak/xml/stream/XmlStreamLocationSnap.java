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

import java.util.Objects;

import javax.xml.stream.Location;

import org.xml.sax.SAXParseException;

/**
 * @author Stephen
 *
 */
public class XmlStreamLocationSnap 
		implements Location {
	private final int _nLine;
	private final int _nColumn;
	private final int _nOffset;
	private final String _idPublic;
	private final String _idSystem;
	
	public XmlStreamLocationSnap(Location locSource) {
		Objects.requireNonNull(locSource, "locSource");
		_nLine = locSource.getLineNumber();
		_nColumn = locSource.getColumnNumber();
		_nOffset = locSource.getCharacterOffset();
		_idPublic = locSource.getPublicId();
		_idSystem = locSource.getSystemId();
	}
	
	public XmlStreamLocationSnap(SAXParseException exception) {
		Objects.requireNonNull(exception, "exception");
		_nLine = exception.getLineNumber();
		_nColumn = exception.getColumnNumber();
		_nOffset = -1;
		_idPublic = exception.getPublicId();
		_idSystem = exception.getSystemId();
	}
	
	public XmlStreamLocationSnap(String publicId, String systemId, int lineNumber, int columnNumber)
	{
		_nLine = lineNumber;
		_nColumn = columnNumber;
		_nOffset = -1;
		_idPublic = publicId;
		_idSystem = systemId;
	}

	/**
	 * Return the line number where the current event ends,
	 * returns -1 if none is available.
	 * @return the current line number
	 */
	@Override
	public int getLineNumber() {
		return _nLine;
	}

	/**
	 * Return the column number where the current event ends,
	 * returns -1 if none is available.
	 * @return the current column number
	 */
	@Override
	public int getColumnNumber() {
		return _nColumn;
	}

	/**
	 * Return the byte or character offset into the input source this location
	 * is pointing to. If the input source is a file or a byte stream then
	 * this is the byte offset into that stream, but if the input source is
	 * a character media then the offset is the character offset.
	 * Returns -1 if there is no offset available.
	 * @return the current offset
	 */
	@Override
	public int getCharacterOffset() {
		return _nOffset;
	}

	/**
	 * Returns the public ID of the XML
	 * @return the public ID, or null if not available
	 */
	public String getPublicId() {
		return _idPublic;
	}

	/**
	 * Returns the system ID of the XML
	 * @return the system ID, or null if not available
	 */
	@Override
	public String getSystemId() {
		return _idSystem;
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
		if (_idPublic != null) {
			sb.append(", pid=");
			sb.append(_idPublic);
		}
		if (_idSystem != null) {
			sb.append(", sid=");
			sb.append(_idSystem);
		}
		sb.append(']');
		return sb.toString();
	}

}
