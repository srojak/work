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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import srojak.core.collections.TQueue;
import srojak.core.collections.TStack;
import srojak.core.collections.TStackReadOnly;
import srojak.xml.XmlParseTextFilter;
import srojak.xml.filters.XmlParseTextNullFilter;


/**
 * @author Stephen
 *
 */
public abstract class XmlEventParserBase 
		implements XmlEventParserState {
	private XmlStreamInputBuilder _builderEvent;
	private XmlParseTextFilter _filterText;
	private TStack<QName> _stackElements;
	private TQueue<String> _queuePendingText;
	private QName _nameElement;
	private boolean _bAtElementStart;
	private int _nEventTypePrior;
	private boolean _bIgnoreExtraWhiteSpace;
	
	public XmlEventParserBase(XmlStreamInputBuilder builder) {
		_builderEvent = builder;
		_filterText = new XmlParseTextNullFilter();
		_stackElements = new TStack<QName>();
		_queuePendingText = new TQueue<String>();
		_nameElement = null;
		_bAtElementStart = false;
		_nEventTypePrior = 0;
		_bIgnoreExtraWhiteSpace = false;
	}

	@Override
	public TStackReadOnly<QName> getElementStack() {
		return _stackElements;
	}

	@Override
	public boolean hasCurrentElement() {
		return _nameElement != null;
	}

	@Override
	public QName getCurrentElementName() {
		return _nameElement;
	}

	@Override
	public String getCurrentNameText() {
		return _nameElement == null ? "" : _nameElement.toString();
	}

	@Override
	public boolean isAtElementStart() {
		return _bAtElementStart;
	}

	@Override
	public boolean ignoreExtraWhiteSpace() {
		return _bIgnoreExtraWhiteSpace;
	}
	
	public XmlParseTextFilter getTextFilter() {
		return _filterText;
	}
	
	public void setTextFilter(XmlParseTextFilter filter) {
		_filterText = filter;
	}
	
	public void setIgnoreExtraWhiteSpace(boolean bState) {
		_bIgnoreExtraWhiteSpace = bState;
	}

	protected void setElementName(QName name) {
		_nameElement = name;
	}
	
	protected int getPriorEventType() {
		return _nEventTypePrior;
	}
	
	private String gatherQueuedText(IXmlParseEventResponse response) {
		StringBuilder sbText = new StringBuilder();
		int nSeq = 0;
		while (!_queuePendingText.isEmpty()) {
			String strText = _queuePendingText.dequeue();
			strText = _filterText.interpretText(_nameElement, strText, nSeq++, this);
			sbText.append(strText);
		}
		return sbText.toString();
	}
	
	protected void processCharacters(Characters event) {
		String strText = _filterText.readCharacters(event, this);
		if (strText != null) {
			_queuePendingText.enqueue(strText);
		}
	}
	
	protected abstract void parseEvent(XMLEvent event, TStack<QName> stackElements,
			IXmlParseEventResponse response)
			throws XMLStreamException;

	public void parse(IXmlParseEventResponse response, InputStream stream) 
			throws XMLStreamException {
		XMLEventReader reader = _builderEvent.createEventReader(stream);
		_stackElements.clear();
		_queuePendingText.clear();
		_nEventTypePrior = 0;
		while (reader.hasNext()) {
			XMLEvent eventNext = reader.nextEvent();
			int nEventType = eventNext.getEventType();
			switch (eventNext.getEventType()) {
			case XMLStreamConstants.START_ELEMENT:
				_bAtElementStart = true;
				_queuePendingText.clear();
				break;
				
			case XMLStreamConstants.CHARACTERS:
				break;
				
			case XMLStreamConstants.END_ELEMENT:
				setElementName(eventNext.asEndElement().getName());
				if (_bAtElementStart) {
					if (!_queuePendingText.isEmpty()) {
						String strText = gatherQueuedText(response);
						response.onElementText(strText, this);
					}
				}
				_bAtElementStart = false;
				break;
				
			default:
				_bAtElementStart = false;
				break;
			}
			parseEvent(eventNext, _stackElements, response);
			_nEventTypePrior = nEventType;
		}
	}
}
