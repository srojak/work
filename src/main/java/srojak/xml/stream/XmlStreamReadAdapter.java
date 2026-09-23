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

import java.io.InputStream;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObservationCollector;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.factories.XmlStreamInputFactory;
import srojak.xml.stream.parse.XmlStreamParser;

/**
 * @author Stephen
 *
 */
public class XmlStreamReadAdapter 
		extends XmlStreamReadAdapterBase
		implements XmlStreamAdapter, IOResultQualifiers {
	private final XmlStreamParser _parser;
	
	public XmlStreamReadAdapter(XmlStreamInputFactory factory, XmlStreamParser parser) {
		super(factory);
		Objects.requireNonNull(parser, "parser");
		_parser = parser;
	}
	
	@Override
	protected ObservationCollector getObservationCollector() {
		return _parser.getObservationCollector();
	}

	@Override
	protected void readCore(InputStream streamIn, XResultIntCarrier result) {
		try {
			XMLStreamReader reader = createStreamReader(result.getActivity(), streamIn);
			_parser.start(reader);
			while (reader.hasNext()) {
				int nEvent = reader.next();
				_parser.interpret(nEvent);
			}
			_parser.completed();
			result.setResult(COMPLETED);
		} catch (XMLStreamException exc) {
			result.caughtException(exc);
		}
	}
}
