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

import java.io.OutputStream;
import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import srojak.core.collections.TStack;
import srojak.xml.XmlIndenter;
/**
 * @author Stephen
 *
 */
public class XmlEventWriter {
	protected final XmlStreamOutputBuilder _builderEvent;
	protected final TStack<ElementEventName> _stackElements;
	private XmlIndenter _indenter;
	private InnerWriter _writer;
	private XMLEventFactory _factoryEvents;

	public XmlEventWriter(XmlStreamOutputBuilder builder) {
		Objects.requireNonNull(builder, "builder");
		_builderEvent = builder;
		_stackElements = new TStack<ElementEventName>();
		_indenter = new XmlIndenter(_stackElements);
		_writer = null;
		_factoryEvents = XMLEventFactory.newInstance();
	}
	
	public boolean isWritingNewLines() {
		return _indenter.isWritingNewLines();
	}
	
	public void writeNewLines(boolean bState) {
		_indenter.writeNewLines(bState);
	}
	
	public int getIndent() {
		return _indenter.getIndent();
	}
	
	public void setIndent(int nSpaces) {
		_indenter.setIndent(nSpaces);
	}
	
	public boolean isOpen() {
		return _writer != null;
	}
	
	public void open(OutputStream output) 
			throws XMLStreamException {
		_writer = new InnerWriter(output);
		_stackElements.clear();
	}
	
	public void close() 
			throws XMLStreamException {
		_writer.close();
		_writer = null;
	}
	
	protected InnerWriter getInnerWriter() {
		return _writer;
	}
	
	protected void writeIndent() 
			throws XMLStreamException {
		if (_indenter.isWritingNewLines()) {
			String strIndent = _indenter.writeNewlineIndent();
			_writer.writeEvent(_factoryEvents.createIgnorableSpace(strIndent));			
		}
	}
	
	public void startDocument() 
			throws XMLStreamException {
		_writer.writeEvent(_factoryEvents.createStartDocument());
	}
	
	public void endDocument() 
			throws XMLStreamException {
		_writer.writeEvent(_factoryEvents.createEndDocument());
	}
	
	public void startElement(QName nameElement)
			throws XMLStreamException {
		writeIndent();
		_writer.writeEvent(_factoryEvents.createStartElement(nameElement, null, null));
		_stackElements.push(new ElementEventName(nameElement));
	}
	
	public void endElement(QName nameElement, boolean bHasContent) 
			throws XMLStreamException {
		@SuppressWarnings("unused")
		ElementEventName een = _stackElements.pop();
		if (bHasContent) {
			writeIndent();
		}
		_writer.writeEvent(_factoryEvents.createEndElement(nameElement, null));
	}
	
	public void writeTextElement(QName nameElement, String strText) 
			throws XMLStreamException {
		writeIndent();
		_writer.writeEvent(_factoryEvents.createStartElement(nameElement, null, null));
		_writer.writeEvent(_factoryEvents.createCharacters(strText));
		_writer.writeEvent(_factoryEvents.createEndElement(nameElement, null));
	}
	
	public void writeAttribute(QName nameAttribute, String strValue) 
			throws XMLStreamException {
		_writer.writeEvent(_factoryEvents.createAttribute(nameAttribute, strValue));
	}
	
	protected class InnerWriter {
		private XMLEventWriter _writerEvent;
		
		public InnerWriter(OutputStream output)
				throws XMLStreamException {
			_writerEvent = _builderEvent.createEventWriter(output);
		}
		
		public void writeEvent(XMLEvent event)
				throws XMLStreamException {
			_writerEvent.add(event);
		}
		
		public void close() 
				throws XMLStreamException {
			_writerEvent.flush();
			_writerEvent.close();
			_writerEvent = null;
		}
	}
}
