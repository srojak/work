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

import javax.xml.namespace.QName;
import javax.xml.stream.events.Characters;

import srojak.core.TextMessageRelay;

/**
 * @author Stephen
 *
 */
public class XmlParseTextFilterPrinting
		implements IXmlParseTextFilter {
	private TextMessageRelay _msgOut;
	private boolean _bShowCharsRead;
	
	public XmlParseTextFilterPrinting(TextMessageRelay msgOut) {
		if (msgOut == null) {
			throw new IllegalArgumentException("msgOut");
		}
		_msgOut = msgOut;
		_bShowCharsRead = false;
	}

	@Override
	public String readCharacters(Characters event, XmlEventParserState state) {
		String strText = event.getData();
		StringBuilder sb = new StringBuilder();
		boolean bAppend = false;
		if (state.isAtElementStart()) {
			sb.append("TXF Element <");
			sb.append(state.getElementName());
			sb.append(">,");
			bAppend = true;
		} else {
			sb.append("X");
			if (strText.isBlank()) {
				if (state.ignoreExtraWhiteSpace()) {
					return null;
				}
			}
		}
		if (_bShowCharsRead) {
			sb.append(" Chars");
			if (strText != null) {
				if (strText.isBlank()) {
					sb.append(" whitespace");
				} else {
					sb.append(" [");
					sb.append(strText);
					sb.append("]");
				}
			}
			_msgOut.writeln(sb.toString());
		}
		if (bAppend) {
			return strText;
		}
		return null;
	}

	@Override
	public String filterCharacters(XmlStreamParserState state, boolean bIgnoreExtraWhiteSpace,
			String strChars) {
		StringBuilder sb = new StringBuilder();
		boolean bAppend = false;
		if (state.isAtElementStart()) {
			sb.append("TXF Element <");
			sb.append(state.getCurrentElementName());
			sb.append(">,");
			bAppend = true;
		} else {
			sb.append("X");
			if (strChars.isBlank()) {
				if (bIgnoreExtraWhiteSpace) {
					return null;
				}
			}
		}
		if (_bShowCharsRead) {
			sb.append(" Chars");
			if (strChars != null) {
				if (strChars.isBlank()) {
					sb.append(" whitespace");
				} else {
					sb.append(" [");
					sb.append(strChars);
					sb.append("]");
				}
			}
			_msgOut.writeln(sb.toString());
		}
		if (bAppend) {
			return strChars;
		}
		return null;
	}

	@Override
	public String interpretText(QName nameElement, String strText, int nOrdinal, XmlEventParserState state) {
		StringBuilder sb = new StringBuilder();
		sb.append("TXF Element <");
		sb.append(state.getElementName());
		sb.append("> seq=");
		sb.append(nOrdinal);
		sb.append(" Chars");
		if (strText != null) {
			if (strText.isBlank()) {
				sb.append(" whitespace");
			} else {
				sb.append(" [");
				sb.append(strText);
				sb.append("]");
			}
		}
		_msgOut.writeln(sb.toString());
		return strText;
	}

	@Override
	public String interpretText(QName nameElement, String strText, int nOrdinal, XmlStreamParserState state) {
		StringBuilder sb = new StringBuilder();
		sb.append("TXF Element <");
		sb.append(state.getCurrentElementName());
		sb.append("> seq=");
		sb.append(nOrdinal);
		sb.append(" Chars");
		if (strText != null) {
			if (strText.isBlank()) {
				sb.append(" whitespace");
			} else {
				sb.append(" [");
				sb.append(strText);
				sb.append("]");
			}
		}
		_msgOut.writeln(sb.toString());
		return strText;
	}


	public void onTextCData(Characters event, XmlEventParserState state) {
		StringBuilder sb = new StringBuilder();
		sb.append("Element <");
		sb.append(state.getElementName());
		sb.append(">, [CDATA [");
		String strText = event.getData();
		sb.append(strText);
		sb.append("]]");
		_msgOut.writeln(sb.toString());
	}

	public void onCharacters(Characters event) {
		StringBuilder sb = new StringBuilder();
		sb.append("X Characters");
		String strText = event.getData();
		if (strText != null) {
			if (strText.isBlank()) {
				sb.append(" whitespace");
			} else {
				sb.append(" [");
				sb.append(strText);
				sb.append("]");
			}
		}
		_msgOut.writeln(sb.toString());
	}
}
