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

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
/**
 * @author Stephen
 *
 */
public interface IXmlParseEventResponse {
	void onStartDocument(XMLEvent event);
	void onEndDocument(XMLEvent event, XmlEventParserState state);
	
	void onStartElement(StartElement event, XmlEventParserState state);
	void onEndElement(EndElement event, XmlEventParserState state);
	
	void onAttribute(Attribute attrib, XmlEventParserState state);
	
	void onElementText(String strText, XmlEventParserState state);
	
	void onComment(Comment event);
}
