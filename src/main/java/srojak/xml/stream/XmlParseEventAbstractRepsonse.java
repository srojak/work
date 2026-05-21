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
public abstract class XmlParseEventAbstractRepsonse 
		implements IXmlParseEventResponse {

	/**
	 * 
	 */
	public XmlParseEventAbstractRepsonse() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStartDocument(XMLEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndDocument(XMLEvent event, XmlEventParserState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartElement(StartElement event, XmlEventParserState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndElement(EndElement event, XmlEventParserState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttribute(Attribute attrib, XmlEventParserState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onElementText(String strText, XmlEventParserState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComment(Comment event) {
		// TODO Auto-generated method stub

	}

}
