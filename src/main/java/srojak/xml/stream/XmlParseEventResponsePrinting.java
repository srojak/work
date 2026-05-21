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
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import srojak.core.TextMessageRelay;
import srojak.core.collections.TStackReadOnly;

/**
 * @author Stephen
 *
 */
public class XmlParseEventResponsePrinting 
		implements IXmlParseEventResponse {
	private TextMessageRelay _msgOut;
	
	public XmlParseEventResponsePrinting(TextMessageRelay msgOut) {
		if (msgOut == null) {
			throw new IllegalArgumentException("msgOut");
		}
		_msgOut = msgOut;
	}
	
	@Override
	public void onStartDocument(XMLEvent event) {
		StringBuilder sb = new StringBuilder();
		sb.append("Start document, loc=");
		StreamLocationTool.FormatLineAndColumn(sb, event.getLocation());
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onEndDocument(XMLEvent event, XmlEventParserState state) {
		StringBuilder sb = new StringBuilder();
		sb.append("End document, loc=");
		StreamLocationTool.FormatLineAndColumn(sb, event.getLocation());
		sb.append(", depth=");
		sb.append(state.getElementStack().depth());
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onStartElement(StartElement event, XmlEventParserState state) {
		TStackReadOnly<QName> stackElements = state.getElementStack();
		StringBuilder sb = new StringBuilder();
		sb.append("Depth=");
		sb.append(stackElements.depth());
		sb.append(" Start Element <");
		sb.append(stackElements.peek());
		sb.append(">, ");
		StreamLocationTool.FormatLineAndColumn(sb, event.getLocation());
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onEndElement(EndElement event, XmlEventParserState state) {
		TStackReadOnly<QName> stackElements = state.getElementStack();
		StringBuilder sb = new StringBuilder();
		sb.append("Depth=");
		sb.append(stackElements.depth());
		sb.append(" End Element <");
		sb.append(stackElements.peek());
		sb.append(">");
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onAttribute(Attribute attrib, XmlEventParserState state) {
		StringBuilder sb = new StringBuilder();
		QName nameAttrib = attrib.getName();
		String strValue = attrib.getValue();
		sb.append("Attribute ");
		sb.append(nameAttrib);
		if (strValue != null) {
			sb.append("=\"");
			sb.append(strValue);
			sb.append("\"");
		}
		sb.append(", ");
		StreamLocationTool.FormatLineAndColumn(sb, attrib.getLocation());
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onElementText(String strText, XmlEventParserState state) {
		StringBuilder sb = new StringBuilder();
		sb.append("Text: Element <");
		sb.append(state.getElementName());
		sb.append(">, \"");
		sb.append(strText);
		sb.append("\"");
		_msgOut.writeln(sb.toString());
	}

	@Override
	public void onComment(Comment event) {
		StringBuilder sb = new StringBuilder();
		sb.append("Comment, ");
		StreamLocationTool.FormatLineAndColumn(sb, event.getLocation());
		_msgOut.writeln(sb.toString());
		_msgOut.writeln("!-- " + event.getText());
	}

}
