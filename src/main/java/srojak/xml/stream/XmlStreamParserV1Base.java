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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.xml.XmlParseTextFilter;
import srojak.xml.stream.impl.XmlParseMethods;
import srojak.xml.stream.impl.XmlStreamParserStateContainer;

/*
 * @author Stephen
 * 
 * @see https://docs.oracle.com/javase/8/docs/api/javax/xml/stream/XMLStreamReader.html
 */
public abstract class XmlStreamParserV1Base
		implements XMLStreamConstants {
	private final XmlStreamInputBuilder _builderStream;
	private final XmlParserOptions _options;
	private final XmlStreamParserStateContainer _state;
	private XMLStreamReader _reader;
	private int _nEventTypePrior;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;
	
	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();
	}

	public XmlStreamParserV1Base(XmlStreamInputBuilder builder) {
		Objects.requireNonNull(builder, "builder");
		_builderStream = builder;
		_options = new XmlParserOptions();
		_state = new XmlStreamParserStateContainer();
		_nEventTypePrior = 0;
	}
	
	protected final XmlStreamParserState getParserState() {
		return _state;
	}
	
	public final XmlParserOptions getOptions() {
		return _options;
	}
	
	protected final int getPriorEventType() {
		return _nEventTypePrior;
	}
	
	protected StreamElementAttribute findAttributeByName(StreamElementAttribute[] attributes,
			QName nameAttribute) {
		for (int index = 0; index < attributes.length; index++) {
			if (attributes[index].getName().equals(nameAttribute)) {
				return attributes[index];
			}
		}
		return null;
	}
	
	protected void parseInit() {
		
	}
	
	protected void parseEndDocument() {
		
	}
	
	protected void parseStartElement(QName nameElement, StreamElementAttribute[] attributes) 
			throws XMLStreamException {
		
	}
	
	protected void parseEndElement(QName nameElement, String strText) {
		
	}
	
	protected void parseComment(String strText) {
		
	}
	
	protected void parseOther(int nEventType) {
		
	}
	
	protected String getElementText() 
			throws XMLStreamException {
		return _reader.getElementText();
	}
	
	private void getCharacters(XMLStreamReader reader, StringBuilder sb) {
		char[] charText = reader.getTextCharacters();
		sb.append(charText);
	}
	
	public final void parse(InputStream input) 
			throws XMLStreamException {
		_reader = _builderStream.createStreamReader(input);
		_nEventTypePrior = 0;
		parseInit();
		while (_reader.hasNext()) {
			int nEvent = _reader.next();
			_state.setCurrentLocation(_reader.getLocation());
			QName nameCurrent;
			XmlParseTextFilter filter;
			switch (nEvent) {
			case START_DOCUMENT:
				break;
				
			case END_DOCUMENT:
				_state.clearAtElementStart();
				parseEndDocument();
				break;
				
			case PROCESSING_INSTRUCTION:
				break;
				
			case START_ELEMENT:
				nameCurrent = _reader.getName();
				{
					_state.startElement(nameCurrent);
					StreamElementAttribute[] attribs = XmlParseMethods.getAttributes(_reader);
					parseStartElement(nameCurrent, attribs);
				}
				break;
				
			case END_ELEMENT:
				nameCurrent = _reader.getName();
				{
					StringBuilder sbText = new StringBuilder();
					_state.endElement(nameCurrent, _options, sbText);
					parseEndElement(nameCurrent, sbText.toString());
				}
				
				break;
				
			case CHARACTERS:
				filter = _options.getTextFilter();
				{
					StringBuilder sbChars = new StringBuilder();
					getCharacters(_reader, sbChars);
					String strText = filter.filterCharacters(_state, 
							_options.ignoreExtraWhiteSpace(), sbChars.toString());
					_state.saveCharacters(strText);
				}
				break;
				
			case CDATA:
				filter = _options.getTextFilter();
				{
					StringBuilder sbChars = new StringBuilder();
					getCharacters(_reader, sbChars);
					String strText = filter.filterCharacters(_state, 
							_options.ignoreExtraWhiteSpace(), sbChars.toString());
					_state.saveCharacters(strText);
				}
				break;
				
			case COMMENT:
				parseComment(_reader.getText());
				break;
				
			default:
				_state.clearAtElementStart();
				parseOther(nEvent);
				break;
			}
			_nEventTypePrior = nEvent;
			
		}
		_reader = null;
		_state.reset();
	}
}
