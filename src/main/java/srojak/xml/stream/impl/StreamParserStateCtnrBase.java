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

import srojak.core.collections.TStack;
import srojak.core.collections.TStackReadOnly;
import srojak.xml.XmlElementContext;
import srojak.xml.stream.XmlStreamParserState;

/**
 * @author Stephen
 *
 */
public abstract class StreamParserStateCtnrBase 
		implements XmlStreamParserState, XmlElementContext {
	private final TStack<QName> _stackElements;
	private boolean _bIsActive;
	private Location _locCurrent;
	private QName _nameCurrent;
	private boolean _bAtElementStart;
	private int _nEventTypePrior;
	
	protected StreamParserStateCtnrBase() {
		_stackElements = new TStack<QName>();
		_bIsActive = false;
		_locCurrent = XmlSourceLocation.NULL;
		_nameCurrent = null;
		_bAtElementStart = false;
		_nEventTypePrior = 0;
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
	public String getCurrentNameText() {
		return _nameCurrent == null ? "" : _nameCurrent.toString();
	}

	@Override
	public boolean isAtElementStart() {
		return _bAtElementStart;
	}
	
	@Override
	public int getPriorEventType() {
		return _nEventTypePrior;
	}
	
	public void reset() {
		_bIsActive = false;
		_locCurrent = XmlSourceLocation.NULL;
		_nameCurrent = null;
		_bAtElementStart = false;		
		_nEventTypePrior = 0;
	}
	
	public void start() {
		_bIsActive = true;
		_nameCurrent = null;
		_stackElements.clear();
		_nEventTypePrior = 0;
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
	
	public void setPriorEventType(int nEventType) {
		_nEventTypePrior = nEventType;
	}
	
	public void startElement(QName nameElement) {
		Objects.requireNonNull(nameElement, "nameElement");
		_bAtElementStart = true;
		_nameCurrent = nameElement;
		_stackElements.push(nameElement);
	}
	
	public void endElement(QName nameElementRead) {
		Objects.requireNonNull(nameElementRead, "nameElementRead");
		@SuppressWarnings("unused")
		QName nameStored = _stackElements.peek();
		_bAtElementStart = false;
	}
}
