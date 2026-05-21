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
package srojak.xml.stream.impl;

import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import srojak.core.collections.TQueue;
import srojak.core.collections.TStack;
import srojak.core.collections.TStackReadOnly;
import srojak.xml.stream.IXmlParseTextFilter;
import srojak.xml.stream.XmlParserOptions;
import srojak.xml.stream.XmlStreamParserState;

/**
 * @author Stephen
 *
 */
public class XmlStreamParserStateContainer 
		implements XmlStreamParserState {
	private final TStack<QName> _stackElements;
	private final TQueue<String> _queuePendingText;
	private boolean _bIsActive;
	private Location _locCurrent;
	private QName _nameCurrent;
	private boolean _bAtElementStart;
	
	public XmlStreamParserStateContainer() {
		_stackElements = new TStack<QName>();
		_queuePendingText = new TQueue<String>();
		_bIsActive = false;
		_locCurrent = XmlSourceLocation.NULL;
		_nameCurrent = null;
		_bAtElementStart = false;
	}

	@Override
	public boolean isActive() {
		return _bIsActive;
	}

	@Override
	public Location getCurentLocation() {
		return _locCurrent;
	}

	@Override
	public TStackReadOnly<QName> getElementStack() {
		return _stackElements;
	}

	@Override
	public boolean hasCurrentElement() {
		return _nameCurrent != null;
	}

	@Override
	public QName getCurrentElementName() {
		return _nameCurrent;
	}

	@Override
	public boolean isAtElementStart() {
		return _bAtElementStart;
	}
	
	public void reset() {
		_bIsActive = false;
		_locCurrent = XmlSourceLocation.NULL;
		_nameCurrent = null;
		_bAtElementStart = false;		
	}
	
	public void start() {
		_bIsActive = true;
		_nameCurrent = null;
		_stackElements.clear();
		_queuePendingText.clear();
	}
	
	public void setCurrentLocation(Location location) {
		_locCurrent = location;
	}
	
	public void clearCurrentElement() {
		_nameCurrent = null;
	}
	
	public void clearAtElementStart() {
		_bAtElementStart = false;
	}
	
	public void saveCharacters(String strChars) {
		_queuePendingText.enqueue(strChars);
	}
	
	public void gatherCollectedText(XmlParserOptions options, StringBuilder sbText) {
		IXmlParseTextFilter filterText = options.getTextFilter();
		int nSeq = 0;
		while (!_queuePendingText.isEmpty()) {
			String strText = _queuePendingText.dequeue();
			strText = filterText.interpretText(_nameCurrent, strText, nSeq++, this);
			sbText.append(strText);
		}
	}
	
	public void startElement(QName nameElement) {
		Objects.requireNonNull(nameElement, "nameElement");
		_queuePendingText.clear();
		_bAtElementStart = true;
		_nameCurrent = nameElement;
		_stackElements.push(nameElement);
	}
	
	public void endElement(QName nameElementRead, XmlParserOptions options, StringBuilder sbText) {
		Objects.requireNonNull(nameElementRead, "nameElementRead");
		Objects.requireNonNull(options, "options");
		QName nameStored = _stackElements.peek();
		if (_bAtElementStart) {
			gatherCollectedText(options, sbText);
		}
		_bAtElementStart = false;
	}
	
	protected TQueue<String> getPendingTextQueue() {
		return _queuePendingText;
	}
}
