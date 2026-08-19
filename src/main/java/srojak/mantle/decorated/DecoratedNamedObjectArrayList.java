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
package srojak.mantle.decorated;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class DecoratedNamedObjectArrayList<V>
		extends ArrayList<DecoratedNamed<V>>
		implements DecoratedNamedObjectList<V> {

	/**
	 * 
	 */
	public DecoratedNamedObjectArrayList() {
		super();
	}

	/**
	 * @param c
	 */
	public DecoratedNamedObjectArrayList(Collection<? extends DecoratedNamed<V>> c) {
		super(c);
	}

	/**
	 * @param initialCapacity
	 */
	public DecoratedNamedObjectArrayList(int initialCapacity) {
		super(initialCapacity);
	}

}
