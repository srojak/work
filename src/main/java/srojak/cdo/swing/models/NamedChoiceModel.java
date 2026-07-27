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
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.events.NameAndStateChangeOriginator;
import srojak.events.CollectionChangeEventOriginator;
import srojak.events.ObjectValueChangeEventOriginator;

/**
 * @author Stephen
 *
 */
public interface NamedChoiceModel 
		extends CDOControlModel, CanBeEnabled, ChangeEventOriginator, 
			CollectionChangeEventOriginator, ObjectValueChangeEventOriginator,
			NameAndStateChangeOriginator, ItemSelectable {

	List<NameIdentifiedAndLabeled> getChoices();
	void setChoices(Collection<? extends NameIdentifiedAndLabeled> items);
	boolean hasSelection();
	NameIdentifiedAndLabeled getSelection();
	void setSelection(NameIdentifiedAndLabeled selection);
	void setSelectionByName(String strName);
	void setChoiceEnabled(String strName, boolean bState);
}
