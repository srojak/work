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

import java.io.*;

import javax.xml.stream.*;

import srojak.core.collections.TStack;
import srojak.xml.XmlIndenter;

/**
 * @author Stephen
 *
 */
public class XmlStreamFmtWriter {
	private XmlStreamOutputBuilder _builderStream;
	private TStack<ElementStreamName> _stackElements;
	private XmlIndenter _indenter;
	private XMLStreamWriter _writer;
	
	public XmlStreamFmtWriter(XmlStreamOutputBuilder builder) {
		_builderStream = builder;
		_stackElements = new TStack<ElementStreamName>();
		_indenter = new XmlIndenter(_stackElements);
		_writer = null;
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
		_writer = _builderStream.createWriter(output);
		_stackElements.clear();
	}
	
	public void close() 
			throws XMLStreamException {
		_writer.flush();
		_writer.close();
		_writer = null;
	}
	
	public void writeStartDocument() 
			throws XMLStreamException {
		_writer.writeStartDocument();
	}
	
	public void writeStartDocument(String strVersion) 
			throws XMLStreamException {
		_writer.writeStartDocument(strVersion);
	}
	
	public void writeStartDocument(String strEncoding, String strVersion) 
			throws XMLStreamException {
		_writer.writeStartDocument(strEncoding, strVersion);
	}
	
	public void writeEndDocument() 
			throws XMLStreamException {
		while (!_stackElements.isEmpty()) {
			writeEndElement();
		}
		_writer.writeEndDocument();
	}
	
	public void writeStartElement(String strLocalName) 
			throws XMLStreamException {
		if (_indenter.isWritingNewLines()) {
			String strIndent = _indenter.writeNewlineIndent();
			_writer.writeCharacters(strIndent);
		}
		_writer.writeStartElement(strLocalName);
		_stackElements.push(new ElementStreamName(strLocalName));
	}
	
	public void writeEndElement() 
			throws XMLStreamException {
		_stackElements.pop();
		if (_indenter.isWritingNewLines()) {
			String strIndent = _indenter.writeNewlineIndent();
			_writer.writeCharacters(strIndent);
		}
		_writer.writeEndElement();
	}
	
	public void writeEmptyElement(String strLocalName) 
			throws XMLStreamException {
		if (_indenter.isWritingNewLines()) {
			String strIndent = _indenter.writeNewlineIndent();
			_writer.writeCharacters(strIndent);
		}
		_writer.writeEmptyElement(strLocalName);
	}
	
	public void writeAttribute(String strLocalName, String strValue) 
			throws XMLStreamException {
		_writer.writeAttribute(strLocalName, strValue);
	}
	
	public void writeText(String strText) 
			throws XMLStreamException {
		_writer.writeCharacters(strText);
	}
	
	public void writeCData(String strText) 
			throws XMLStreamException {
		_writer.writeCData(strText);
	}
}
