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
package srojak.cdo.swing.base;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.CanBeEnabled;
import srojak.cdo.events.AWTEventMethods;
import srojak.cdo.swing.CDOControlModel;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.tools.BitMethods;
import srojak.core.tools.ListMethods;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeEventOriginator;
import srojak.events.CollectionChangeListener;

/**
 * @author Stephen
 *
 */
public abstract class SelectionControlModelBase<T>
		implements CDOControlModel, CanBeEnabled, ChangeEventOriginator, 
			CollectionChangeEventOriginator, ItemSelectable {
	// TODO: extend ControlModelBase
	protected final CommonEventListenerStore _listeners;
	private final LinkedList<T> _listItems;
	private final LinkedList<T> _listSelection;
	private int _flags;
	
	public static final int F_ALLOW_MULTISELECT = 0x1;
	public static final int F_ENABLED = 0x2;
	
	protected SelectionControlModelBase() {
		_listeners = new CommonEventListenerList();
		_listItems = new LinkedList<T>();
		_listSelection = new LinkedList<T>();
		_flags = 0;
	}
	
	protected void setFlags(int ... masks) {
		_flags = BitMethods.set(_flags, masks);
	}
	
	protected void clearFlags(int ... masks) {
		_flags = BitMethods.clear(_flags, masks);
	}
	
	protected boolean testFlags(int mask) {
		return BitMethods.test(mask, mask);
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
	
	public List<T> getChoices() {
		return List.copyOf(_listItems);
	}
	
	protected void clearPriorChoices() {
		_listItems.clear();
		CollectionChangeEvent event = new CollectionChangeEvent(this, CollectionChangeEvent.VERB_CLEAR);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
		clearSelection();
	}
	
	public void setChoices(Collection<? extends T> items) {
		Objects.requireNonNull(items, "items");
		_listItems.addAll(items);
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_ADD_MULT);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
	}
	
	protected void addChoice(T item) {
		Objects.requireNonNull(item, "item");
		_listItems.add(item);
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_ADD, item);
		_listeners.forEach(CollectionChangeListener.class, ls -> ls.collectionChanged(event));
	}
	
	protected T findChoice(Predicate<T> predicate) {
		return ListMethods.findInList(_listItems, predicate);
	}
	
	protected T getChoiceByIndex(int index) {
		return _listItems.get(index);
	}
	
	protected boolean isSelectionEmpty() {
		return _listSelection.isEmpty();
	}
	
	protected int getSelectionCount() {
		return _listSelection.size();
	}
	
	protected T getFirstSelectedItem() {
		return _listSelection.peekFirst();
	}
	
	protected void clearSelection() {
		while (!_listSelection.isEmpty()) {
			T entry = _listSelection.removeFirst();
			ItemEvent event = AWTEventMethods.createItemSelectionEvent(this, entry, false);
			_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(event));
		}
	}
	
	protected void addSelection(T entry) {
		if (!BitMethods.test(_flags, F_ALLOW_MULTISELECT)) {
			clearSelection();
		}
		_listSelection.addLast(entry);
		ItemEvent event = AWTEventMethods.createItemSelectionEvent(this, entry, true);
		_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(event));
	}
	
	protected boolean removeSelectionIfPresent(T entry) {
		if (_listSelection.remove(entry)) {
			ItemEvent event = AWTEventMethods.createItemSelectionEvent(this, entry, false);
			_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(event));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object[] getSelectedObjects() {
		return _listSelection.toArray();
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
	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);	
	}

	@Override
	public void removeChangeListener(ChangeListener listener) {
		_listeners.remove(ChangeListener.class, listener);	
	}

}
