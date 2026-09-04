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
import javax.xml.stream.XMLStreamException;

import srojak.xml.stream.work.XmlStreamWorkItemMap;

/**
 * @author Stephen
 *
 */
public class StreamVisibleParser 
		extends XmlStreamActionParserBase {

	/**
	 * 
	 */
	public StreamVisibleParser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseStartElement(QName nameElement, XmlStreamWorkItemMap mapWork, StreamElementAttributeSet attribs)
			throws XMLStreamException {

	}

	@Override
	protected void parseEndElement(QName nameElement, XmlStreamWorkItemMap mapWork, String strElementText) {
		// TODO Auto-generated method stub

	}

}
