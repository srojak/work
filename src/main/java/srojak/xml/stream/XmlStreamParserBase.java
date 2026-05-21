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

import srojak.xml.stream.impl.StreamElementAttribute;
import srojak.xml.stream.impl.XmlStreamParserStateContainer;

/*
 * @author Stephen
 * 
 * @see https://docs.oracle.com/javase/8/docs/api/javax/xml/stream/XMLStreamReader.html
 */
public abstract class XmlStreamParserBase
		implements XMLStreamConstants {
	private final XmlStreamInputBuilder _builderStream;
	private final XmlParserOptions _options;
	private final XmlStreamParserStateContainer _state;
	private int _nEventTypePrior;

	public XmlStreamParserBase(XmlStreamInputBuilder builder) {
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
	
	protected XmlElementAttribute findAttributeByName(XmlElementAttribute[] attributes,
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
	
	protected void parseStartElement(QName nameElement, XmlElementAttribute[] attributes) {
		
	}
	
	protected void parseEndElement(QName nameElement, String strText) {
		
	}
	
	protected void parseComment(String strText) {
		
	}
	
	protected void parseOther(int nEventType) {
		
	}
	
	private void getCharacters(XMLStreamReader reader, StringBuilder sb) {
		char[] charText = reader.getTextCharacters();
		sb.append(charText);
	}
	
	private XmlElementAttribute[] getAttributes(XMLStreamReader reader) {
		int nCount = reader.getAttributeCount();
		XmlElementAttribute[] array = new XmlElementAttribute[nCount];
		for (int index = 0; index < nCount; index++) {
			array[index] = new StreamElementAttribute(reader, index);
		}
		return array;
	}
	
	public final void parse(InputStream input) 
			throws XMLStreamException {
		XMLStreamReader reader = _builderStream.createStreamReader(input);
		_nEventTypePrior = 0;
		parseInit();
		while (reader.hasNext()) {
			int nEvent = reader.next();
			_state.setCurrentLocation(reader.getLocation());
			QName nameCurrent;
			IXmlParseTextFilter filter;
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
				nameCurrent = reader.getName();
				_state.startElement(nameCurrent);
				parseStartElement(nameCurrent, getAttributes(reader));
				break;
				
			case END_ELEMENT:
				nameCurrent = reader.getName();
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
					getCharacters(reader, sbChars);
					String strText = filter.filterCharacters(_state, 
							_options.ignoreExtraWhiteSpace(), sbChars.toString());
					_state.saveCharacters(strText);
				}
				break;
				
			case CDATA:
				filter = _options.getTextFilter();
				{
					StringBuilder sbChars = new StringBuilder();
					getCharacters(reader, sbChars);
					String strText = filter.filterCharacters(_state, 
							_options.ignoreExtraWhiteSpace(), sbChars.toString());
					_state.saveCharacters(strText);
				}
				break;
				
			case COMMENT:
				parseComment(reader.getText());
				break;
				
			default:
				_state.clearAtElementStart();
				parseOther(nEvent);
				break;
			}
			_nEventTypePrior = nEvent;
			
		}
		_state.reset();
	}
}
