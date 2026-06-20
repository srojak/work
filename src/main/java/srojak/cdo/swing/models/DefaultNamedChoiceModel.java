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
	public void addObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.add(ObjectValueChangeListener.class, listener);		
	}

	@Override
	public void removeObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.remove(ObjectValueChangeListener.class, listener);
	}
}
