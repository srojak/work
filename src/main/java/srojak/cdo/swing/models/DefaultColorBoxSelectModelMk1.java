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
package srojak.cdo.swing.models;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.events.AWTEventMethods;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.tools.BitMethods;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeListener;
import srojak.events.CollectionSizeChangeListener;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * @author Stephen
 *
 */
public class DefaultColorBoxSelectModelMk1 
		implements ColorBoxSelectModel {
	private final CommonEventListenerStore _listeners;
	private final LinkedList<Color> _listColors;
	private Color _colorSelected;
	private int _flags;
	
	public static final int F_ENABLED = 0x1;
	
	public DefaultColorBoxSelectModelMk1() {
		_listeners = new CommonEventListenerList();
		_listColors = new LinkedList<Color>();
		_colorSelected = null;
		_flags = 0;
	}

	@Override
	public boolean isEnabled() {
		return BitMethods.test(_flags,  F_ENABLED);
	}

	@Override
	public void setEnabled(boolean bState) {
		if (bState) {
			_flags = BitMethods.set(_flags, F_ENABLED);
		} else {
			_flags = BitMethods.clear(_flags, F_ENABLED);
		}
		fireStateChanged();
	}
	
	protected void fireStateChanged() {
		_listeners.sendToAll(ChangeListener.class, () -> new ChangeEvent(this), 
				(ls, e) -> ls.stateChanged(e));
	}
	
	private void clearPriorChoices() {
		_listColors.clear();
		CollectionChangeEvent event = new CollectionChangeEvent(this, CollectionChangeEvent.VERB_CLEAR);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
	}
	
	@SuppressWarnings("unused")
	private void addColorChoice(Color color) {
		_listColors.addLast(color);
		CollectionChangeEvent event
				= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_ADD, color);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
	}

	@Override
	public List<Color> getChoices() {
		return _listColors;
	}

	@Override
	public void setChoices(Collection<? extends Color> providers) {
		Objects.requireNonNull(providers, "providers");
		clearPriorChoices();
		_listColors.addAll(providers);
		_colorSelected = _listColors.getFirst();
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_ADD_MULT);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
	}

	@Override
	public Color getSelection() {
		return _colorSelected;
	}

	@Override
	public void setSelection(Color color) {
		if (!_listColors.contains(color)) {
			throw new IllegalArgumentException("color is not in choice set");
		}
		if (_colorSelected != null) {
			ItemEvent eventItem2 = AWTEventMethods.createItemSelectionEvent(this, _colorSelected, false);
			_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(eventItem2));
		}
		_colorSelected = color;
		ItemEvent eventItem = AWTEventMethods.createItemSelectionEvent(this, _colorSelected, true);
		_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(eventItem));
		ObjectValueChangeEvent event 
			= new ObjectValueChangeEvent(this, _colorSelected);
		_listeners.forEach(ObjectValueChangeListener.class, ls -> ls.update(event));
	}

	@Override
	public void setSelection(int index) {
		Color c = _listColors.get(index);
		setSelection(c);
	}

	@Override
	public Object[] getSelectedObjects() {
		return null;
	}

	@Override
	public void addObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.add(ObjectValueChangeListener.class, listener);
	}

	@Override
	public void removeObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.remove(ObjectValueChangeListener.class, listener);
	}

	@Override
	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);	
	}

	@Override
	public void removeChangeListener(ChangeListener listener) {
		_listeners.remove(ChangeListener.class, listener);	
	}

	@Override
	public void addItemListener(ItemListener l) {
		_listeners.add(ItemListener.class, l);		
	}

	@Override
	public void removeItemListener(ItemListener l) {
		_listeners.remove(ItemListener.class, l);		
	}

	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		_listeners.add(CollectionChangeListener.class, listener);		
	}

	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		_listeners.remove(CollectionChangeListener.class, listener);
	}

	@Override
	public void addCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.add(CollectionSizeChangeListener.class, listener);
	}

	@Override
	public void removeCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.remove(CollectionSizeChangeListener.class, listener);
	}

}
