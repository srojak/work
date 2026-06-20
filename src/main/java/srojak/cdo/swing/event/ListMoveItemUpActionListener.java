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
package srojak.cdo.swing.event;

import java.awt.event.ActionEvent;

import srojak.cdo.swing.ScrollableListComponent;
import srojak.cdo.swing.base.ListModelActionListenerBase;
import srojak.cdo.swing.models.OrderableListModel;

/**
 * @author Stephen
 *
 */
public class ListMoveItemUpActionListener<E>
		extends ListModelActionListenerBase<E> {

	/**
	 * @param list
	 */
	public ListMoveItemUpActionListener(ScrollableListComponent<E> list) {
		super(list);
		if (!(_modelList instanceof OrderableListModel)) {
			throw new IllegalArgumentException("list model is not orderable");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = _modelSelection.getMinSelectionIndex();
		((OrderableListModel) _modelList).moveIndexedElementForward(index);
	}

}
