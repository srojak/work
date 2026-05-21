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

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import srojak.core.collections.TStack;

/**
 * @author Stephen
 *
 */
public class XmlEventParserCommon
		extends XmlEventParserBase {

	/**
	 * @param builder
	 */
	public XmlEventParserCommon(XmlStreamInputBuilder builder) {
		super(builder);
	}
	
	private void parseStartElement(IXmlParseEventResponse response, 
			TStack<QName> stackElements, StartElement event) {
		QName nameElement = event.getName();
		setElementName(nameElement);
		stackElements.push(nameElement);
		response.onStartElement(event, this);
		Iterator<Attribute> iterAttribs = event.getAttributes();
		while (iterAttribs.hasNext()) {
			Attribute attr = iterAttribs.next();
			response.onAttribute(attr, this);
		}
	}
	
	private void parseEndElement(IXmlParseEventResponse response, 
			TStack<QName> stackElements, EndElement event) {
		QName nameElement = event.getName();
		response.onEndElement(event, this);
		stackElements.pop();
		nameElement = stackElements.peekSafely();
		setElementName(nameElement);
	}

	@Override
	protected void parseEvent(XMLEvent event, TStack<QName> stackElements, IXmlParseEventResponse response)
			throws XMLStreamException {
		if (event.isStartDocument()) {
			response.onStartDocument(event);
		} else if (event.isEndDocument()) {
			response.onEndDocument(event, this);
		} else if (event.isStartElement()) {
			parseStartElement(response, stackElements, event.asStartElement());
		} else if (event.isEndElement()) {
			parseEndElement(response, stackElements, event.asEndElement());
		} else if (event.isCharacters()) {
			Characters eventChars = event.asCharacters();
			processCharacters(eventChars);
		} else {
			switch (event.getEventType()) {
			case XMLStreamConstants.COMMENT:
				response.onComment((Comment) event);
				break;
				
			default:
				break;
			}
		}

	}

}
