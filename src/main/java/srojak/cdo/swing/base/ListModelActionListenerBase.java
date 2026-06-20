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

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.ListSelectionModel;

import srojak.cdo.swing.ScrollableListComponent;
import srojak.cdo.swing.models.ModifiableListModel;

/**
 * @author Stephen
 *
 */
public abstract class ListModelActionListenerBase<E>
		implements ActionListener {
	protected final ModifiableListModel<E> _modelList;
	protected final ListSelectionModel _modelSelection;

	protected ListModelActionListenerBase(ModifiableListModel<E> modelList,
				ListSelectionModel modelSelection) {
		Objects.requireNonNull(modelList, "modelList");
		Objects.requireNonNull(modelSelection, "modelSelection");
		_modelList = modelList;
		_modelSelection = modelSelection;
	}
	
	protected ListModelActionListenerBase(ScrollableListComponent<E> list) {
		Objects.requireNonNull(list, "list");
		_modelList = list.getListModel();
		_modelSelection = list.getListSelectionModel();	
	}
}
