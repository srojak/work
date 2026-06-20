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
package srojak.cdo.swing;

import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

/**
 * @author Stephen
 *
 * Methods a component with a ListModel can offer.
 */
public interface ListComponent<E> {

	/**
	 * Returns the value at the specified index.
	 * @param index the requested index
	 * @return the value at <code>index</code>
	 */
	 E getElementAt(int index);
	
	/**
	 * Returns true if no indices are selected.
	 *
	 * @return {@code true} if no indices are selected.
	 */
	boolean isSelectionEmpty();
	
	/**
     * Returns an array of all of the selected indices in the selection model,
     * in increasing order.
	 * @return All of the selected indices, in increasing order,
     *         or an empty array if nothing is selected.
	 */
	int[] getSelectedIndices();
	
	/**
	 * Adds a listener to the list that's notified each time a change
	 * to the data model occurs.
	 * @param listener the <code>ListDataListener</code> to be added
	 */
	void addListDataListener(ListDataListener listener);
	
	/**
	 * Removes a listener from the list that's notified each time a
	 * change to the data model occurs.
	 * @param listener the <code>ListDataListener</code> to be removed
	 */
	void removeListDataListener(ListDataListener listener);
		
	/**
	 * Add a listener to the list that's notified each time a change
	 * to the selection occurs.
	 *
	 * @param listener the ListSelectionListener
	 * @see #removeListSelectionListener
	 * @see ListSelectionModel
	 */
	void addListSelectionListener(ListSelectionListener listener);
	
	/**
	 * Remove a listener from the list that's notified each time a
	 * change to the selection occurs.
	 *
	 * @param listener the ListSelectionListener
	 * @see #addListSelectionListener
	 * @see ListSelectionModel
	 */
	void removeListSelectionListener(ListSelectionListener listener);
}
