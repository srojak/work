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

import java.util.HashMap;
import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.events.ObjectPropertyChangeEvent;
import srojak.core.events.ObjectPropertyChangeListener;
import srojak.core.events.ObjectPropertyChangeOriginator;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.mutable.BooleanMutable;
import srojak.xml.XmlParseTextFilter;
import srojak.xml.filters.XmlParseTextNullFilter;

/**
 * @author Stephen
 *
 */
public class XmlParserOptions
		implements ObjectPropertyChangeOriginator {
	private final HashMap<NameToken, BooleanMutable> _mapFlags;
	private final SingleEventListenerStore<ObjectPropertyChangeListener> _listeners;
	private XmlParseTextFilter _filterText;
	
	public static final NameToken PROPERTY_TEXT_FILTER = NameToken.factory("ParseTextFilter");
	public static final NameToken PROPERTY_IGNORE_WS = NameToken.factory("IgnoreExtraWhiteSpace");
	public static final NameToken PROPERTY_RECORD_COMMENTS = NameToken.factory("RecordComments");

	public XmlParserOptions() {
		_mapFlags = new HashMap<NameToken, BooleanMutable>();
		_listeners = new SingleEventListenerList<ObjectPropertyChangeListener>();
		_filterText = new XmlParseTextNullFilter();
		_mapFlags.put(PROPERTY_IGNORE_WS, new BooleanMutable(false));
		_mapFlags.put(PROPERTY_RECORD_COMMENTS, new BooleanMutable(false));
	}
	
	public boolean getFlag(NameToken tokenName) {
		Objects.requireNonNull(tokenName, "tokenName");
		BooleanMutable flag = _mapFlags.get(tokenName);
		return flag == null ? false : flag.getValue();
	}
	
	public void defineFlagIfNotPresent(NameToken tokenName) {
		Objects.requireNonNull(tokenName, "tokenName");
		BooleanMutable flag = _mapFlags.get(tokenName);
		if (flag == null) {
			flag = new BooleanMutable(false);
			_mapFlags.put(tokenName, flag);
		}
	}
	
	public void setFlag(NameToken tokenName, boolean bState) {
		Objects.requireNonNull(tokenName, "tokenName");
		BooleanMutable flag = _mapFlags.get(tokenName);
		boolean bChange = false;
		if (flag == null) {
			flag = new BooleanMutable(bState);
			_mapFlags.put(tokenName, flag);
			bChange = true;
		} else {
			bChange = flag.setValue(bState);
		}
		if (bChange) {
			_listeners.sendToAll(() -> new ObjectPropertyChangeEvent(this, tokenName, Boolean.valueOf(bState)),
					(ls, ev) -> ls.propertyChanged(ev));
		}
	}

	@Deprecated
	public boolean ignoreExtraWhiteSpace() {
		return getFlag(PROPERTY_IGNORE_WS);
	}
	
	public XmlParseTextFilter getTextFilter() {
		return _filterText;
	}
	
	public void setTextFilter(XmlParseTextFilter filter) {
		Objects.requireNonNull(filter, "filter");
		_filterText = filter;
		_listeners.sendToAll(() -> new ObjectPropertyChangeEvent(this, PROPERTY_TEXT_FILTER, _filterText),
				(ls, ev) -> ls.propertyChanged(ev));
	}

	@Override
	public void addObjectPropertyChangeListener(ObjectPropertyChangeListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeObjectPropertyChangeListener(ObjectPropertyChangeListener listener) {
		_listeners.remove(listener);
	}
}
