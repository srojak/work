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

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.observe.HasSingleObservationCollector;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public interface XmlStreamParser 
		extends HasSingleObservationCollector, XmlStreamParserOptions {

	void start(XMLStreamReader reader);
	void interpret(int nEvent)
			throws XMLStreamException;
	void writeError(XmlStreamParseErrorDescr error);
	void completed();
	boolean hasParseErrors();
	List<XmlStreamParseErrorDescr> getParseErrors();
}
