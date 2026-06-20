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
import java.util.List;

import javax.swing.ListModel;

import srojak.cdo.swing.CDOControlModel;

/**
 * @author Stephen
 *
 */
public interface ModifiableListModel<E>
		extends CDOControlModel, ListModel<E> {
	boolean containsElement(E e);
	int indexOfElement(E e);
	void clear();
	boolean addElement(E e);
	boolean insertElement(int index, E e);
	boolean removeElement(E e);
	boolean removeElementAt(int index);
	List<E> getElements();
	boolean addElements(Collection<? extends E> collection);
}
