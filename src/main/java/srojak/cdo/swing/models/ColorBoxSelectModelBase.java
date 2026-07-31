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
import srojak.cdo.swing.CDOControlModel;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.events.CollectionChangeEventOriginator;
import srojak.events.ObjectValueChangeEventOriginator;

/**
 * @author Stephen
 *
 * @param <C> A type that either is a {@code Color} or an object bearing color.
 */
public interface ColorBoxSelectModelBase<C> 
		extends CDOControlModel, CanBeEnabled, ObjectValueChangeEventOriginator, 
			ChangeEventOriginator, CollectionChangeEventOriginator, ItemSelectable {

	List<C> getChoices();
	void setChoices(Collection<? extends C> providers);
	C getSelection();
	void setSelection(C color);
	void setSelection(int index);
}
