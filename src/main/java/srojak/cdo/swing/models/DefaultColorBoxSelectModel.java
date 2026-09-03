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
import java.util.Collection;
import java.util.Objects;

import srojak.cdo.swing.base.SelectionControlModelBase;
import srojak.events.CollectionSizeChangeEvent;
import srojak.events.CollectionSizeChangeListener;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * @author Stephen
 *
 */
public class DefaultColorBoxSelectModel 
		extends SelectionControlModelBase<Color>
		implements ColorBoxSelectModel {
	
	public DefaultColorBoxSelectModel() {
		super();
	}

	@Override
	public Color getSelection() {
		return getFirstSelectedItem();
	}

	@Override
	public void setSelection(Color color) {
		Objects.requireNonNull(color, "color");
		addSelection(color);
		ObjectValueChangeEvent event 
			= new ObjectValueChangeEvent(this, color);
		_listeners.forEach(ObjectValueChangeListener.class, ls -> ls.update(event));
		CollectionSizeChangeEvent eventSize = new CollectionSizeChangeEvent(this,
				super.getSelectionCount());
		_listeners.forEach(CollectionSizeChangeListener.class, ls -> ls.sizeChanged(eventSize));
	}

	@Override
	public void setSelection(int index) {
		Color color = getChoiceByIndex(index);
		setSelection(color);
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
	public void addCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.add(CollectionSizeChangeListener.class, listener);
	}

	@Override
	public void removeCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.remove(CollectionSizeChangeListener.class, listener);
	}

	@Override
	public void setChoices(Collection<? extends Color> providers) {
		super.setChoices(providers);
		if (!providers.isEmpty()) {
			setSelection(0);
		}
	}
}
