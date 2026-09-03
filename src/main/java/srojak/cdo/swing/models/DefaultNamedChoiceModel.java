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

import java.util.Collection;
import java.util.Objects;

import srojak.cdo.swing.base.SelectionControlModelBase;
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.events.NameAndStateChangeEvent;
import srojak.core.events.NameAndStateChangeListener;
import srojak.core.events.StateChangeCodes;
import srojak.events.CollectionSizeChangeEvent;
import srojak.events.CollectionSizeChangeListener;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * @author Stephen
 *
 */
public class DefaultNamedChoiceModel 
		extends SelectionControlModelBase<NameIdentifiedAndLabeled> 
		implements NamedChoiceModel {
	
	public DefaultNamedChoiceModel() {
		super();
	}

	@Override
	public boolean hasSelection() {
		return !isSelectionEmpty();
	}

	@Override
	public NameIdentifiedAndLabeled getSelection() {
		return getFirstSelectedItem();
	}

	@Override
	public void setSelection(NameIdentifiedAndLabeled selection) {
		Objects.requireNonNull(selection, "selection");
		addSelection(selection);
		ObjectValueChangeEvent event 
				= new ObjectValueChangeEvent(this, selection);
		_listeners.forEach(ObjectValueChangeListener.class, ls -> ls.update(event));
		CollectionSizeChangeEvent eventSize = new CollectionSizeChangeEvent(this, getSelectionCount());
		_listeners.forEach(CollectionSizeChangeListener.class, ls -> ls.sizeChanged(eventSize));
	}

	@Override
	public void setSelectionByName(String strName) {
		NameIdentifiedAndLabeled item = findChoice(i -> i.isNameEqual(strName));
		if (item != null) {
			setSelection(item);
		}
	}

	@Override
	public void setChoices(Collection<? extends NameIdentifiedAndLabeled> items) {
		super.setChoices(items);
	}

	@Override
	public void setChoiceEnabled(String strName, boolean bState) {
		Objects.requireNonNull(strName, "strName");
		NameIdentifiedAndLabeled item = findChoice(i -> i.isNameEqual(strName));
		if (item != null) {
			NameAndStateChangeEvent event = new NameAndStateChangeEvent(this, strName, StateChangeCodes.SC_CHOICE, bState);
			_listeners.forEach(NameAndStateChangeListener.class, ls -> ls.stateChanged(event));
			if (!bState) {
				// is this item selected?
				removeSelectionIfPresent(item);
			}
		}
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
	public void addNameAndStateChangeListener(NameAndStateChangeListener listener) {
		_listeners.add(NameAndStateChangeListener.class, listener);
	}

	@Override
	public void removeNameAndStateChangeListener(NameAndStateChangeListener listener) {
		_listeners.remove(NameAndStateChangeListener.class, listener);
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
