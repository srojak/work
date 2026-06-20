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
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.event.ChangeListener;

import srojak.cdo.ColorSelectionProvider;
import srojak.cdo.events.ColorValueChangeListener;
import srojak.cdo.swing.base.SelectionControlModelBase;
import srojak.events.CollectionChangeListener;
import srojak.events.ObjValueChangeEvent;

/**
 * @author Stephen
 *
 */
public class DefaultColorBoxSelectModel 
		extends SelectionControlModelBase<ColorSelectionProvider>
		implements ColorBoxSelectModel {
	
	public DefaultColorBoxSelectModel() {
		super();
	}

	@Override
	public ColorSelectionProvider getSelection() {
		return getFirstSelectedItem();
	}

	@Override
	public void setSelection(ColorSelectionProvider color) {
		Objects.requireNonNull(color, "color");
		addSelection(color);
		ObjValueChangeEvent<Color> event 
			= new ObjValueChangeEvent<Color>(this, color.getSelectionColor());
		_listeners.forEach(ColorValueChangeListener.class, ls -> ls.update(event));
	}

	@Override
	public void addColorValueChangeListener(ColorValueChangeListener listener) {
		_listeners.add(ColorValueChangeListener.class, listener);
	}

	@Override
	public void removeColorValueChangeListener(ColorValueChangeListener listener) {
		_listeners.remove(ColorValueChangeListener.class, listener);
	}
}
