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
package srojak.cdo.swing.styles;

import java.util.Objects;
import java.util.function.BiFunction;

import javax.swing.text.AttributeSet;

import srojak.cdo.swing.StyleName;
import srojak.core.events.MapChangeVerbEvent;
import srojak.core.events.MapChangeVerbListener;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.excepts.DuplicateKeyException;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NamedStyleMap 
		extends KeyedStyleMap<StyleName> 
		implements NamedStyleMapReadOnly {
	private final SingleEventListenerStore<MapChangeVerbListener> _listeners;

	public NamedStyleMap() {
		super();
		_listeners = new SingleEventListenerList<MapChangeVerbListener>();
	}

	@Override
	public void clear() {
		super.clear();
		MapChangeVerbEvent event = new MapChangeVerbEvent(this, MapChangeVerbEvent.VERB_CLEAR);
		_listeners.forEach(ls -> ls.mapChanged(event));
	}

	@Override
	public AttributeSet put(StyleName key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		if (containsKey(key)) {
			throw new DuplicateKeyException();
		}
		AttributeSet stylePrior = super.put(key, value);
		MapChangeVerbEvent event = new MapChangeVerbEvent(this, MapChangeVerbEvent.VERB_ADD, key, value);
		_listeners.forEach(ls -> ls.mapChanged(event));
		return stylePrior;
	}

	@Override
	public AttributeSet remove(Object key) {
		AttributeSet stylePrior = super.remove(key);
		if (stylePrior != null) {
			MapChangeVerbEvent event = new MapChangeVerbEvent(this, MapChangeVerbEvent.VERB_REMOVE, key, stylePrior);
			_listeners.forEach(ls -> ls.mapChanged(event));
		}
		return stylePrior;
	}

	@Override
	public boolean remove(Object key, Object value) {
		boolean bResult = super.remove(key, value);
		if (bResult) {
			MapChangeVerbEvent event = new MapChangeVerbEvent(this, MapChangeVerbEvent.VERB_REMOVE, key, value);
			_listeners.forEach(ls -> ls.mapChanged(event));
		}
		return bResult;
	}

	@Override
	public boolean replace(StyleName key, AttributeSet oldValue, AttributeSet newValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeSet replace(StyleName key, AttributeSet value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeSet merge(StyleName key, AttributeSet value,
			BiFunction<? super AttributeSet, ? super AttributeSet, ? extends AttributeSet> remappingFunction) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replaceAll(BiFunction<? super StyleName, ? super AttributeSet, ? extends AttributeSet> function) {
		throw new UnsupportedOperationException();
	}
	
	public void addChangeListener(MapChangeVerbListener listener) {
		_listeners.add(listener);
	}
	
	public void removeChangeListener(MapChangeVerbListener listener) {
		_listeners.remove(listener);
	}
}
