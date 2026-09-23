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
package srojak.xml.stream.parse;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
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
		ObservationCollector writer = getObservationCollector();
		writer.write(ObsLevel.INFO, "start element " + nameElement + ", " + attribs.size() + " attributes");
		attribs.forEach(a -> {
			writer.buildAndWrite(ObsLevel.DETAIL, sb -> {
				sb.append("attribute ");
				sb.append(a.getName());
				if (a.hasValue()) {
					sb.append(" value=\"");
					sb.append(a.getValue());
					sb.append("\"");
				}
				if (!a.isSpecified()) {
					sb.append(", not specified");
				}
			});
		});
	}

	@Override
	protected void parseEndElement(QName nameElement, XmlStreamWorkItemMap mapWork, String strElementText) {
		ObservationCollector writer = getObservationCollector();
		writer.write(ObsLevel.INFO, "end element " + nameElement);
		if (strElementText != null) {
			writer.write(ObsLevel.DETAIL, "text length=" + strElementText.length());
		}
	}

	@Override
	protected void parseComment(String strText) {
		ObservationCollector writer = getObservationCollector();
		writer.write(ObsLevel.DETAIL, "comment = \"" + strText + "\"");
	}

}
