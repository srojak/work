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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

/**
 * @author Stephen
 *
 */
public class StreamReaderParsingDelegate
		extends StreamReaderDelegate {
	private final XmlStreamActionParserBase _parser;

	/**
	 * 
	 */
	public StreamReaderParsingDelegate(XmlStreamActionParserBase parser) {
		super();
		Objects.requireNonNull(parser, "parser");
		_parser = parser;
	}

	/**
	 * @param reader
	 */
	public StreamReaderParsingDelegate(XmlStreamActionParserBase parser, XMLStreamReader reader) {
		super(reader);
		Objects.requireNonNull(parser, "parser");
		_parser = parser;
	}

	public void start() {
		_parser.start(this);
	}
	
	public void completed() {
		_parser.completed();
	}

	@Override
	public int next() 
			throws XMLStreamException {
		int nEvent = super.next();
		_parser.interpret(nEvent);
		return nEvent;
	}
}
