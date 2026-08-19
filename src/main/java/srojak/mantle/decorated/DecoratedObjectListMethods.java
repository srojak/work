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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Stephen
 *
 */
public class DecoratedObjectListMethods {

	public <D extends DecoratedObject<?>> List<D> findAllWhere(Collection<D> collection,
			Predicate<D> predicate) {
		List<D> list = new LinkedList<D>();
		collection.forEach(i -> {
			if (predicate.test(i)) {
				list.add(i);
			}
		});
		return list;
	}
}
