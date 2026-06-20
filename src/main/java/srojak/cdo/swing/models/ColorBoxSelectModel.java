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

import java.awt.ItemSelectable;
import java.util.Collection;
import java.util.List;

import srojak.cdo.CanBeEnabled;
import srojak.cdo.ColorSelectionProvider;
import srojak.cdo.events.ColorValueChangeEventOriginator;
import srojak.cdo.swing.CDOControlModel;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.events.CollectionChangeEventOriginator;

/**
 * @author Stephen
 *
 */
public interface ColorBoxSelectModel
		extends CDOControlModel, CanBeEnabled, ColorValueChangeEventOriginator, 
			ChangeEventOriginator, CollectionChangeEventOriginator, ItemSelectable {
	
	List<ColorSelectionProvider> getChoices();
	void setChoices(Collection<? extends ColorSelectionProvider> providers);
	ColorSelectionProvider getSelection();
	void setSelection(ColorSelectionProvider color);
}
