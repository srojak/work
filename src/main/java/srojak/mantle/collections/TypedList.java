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
package srojak.mantle.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.core.ClassIdentifiedElementsStrict;
import srojak.core.ClassIdentifiedListCommon;

/**
 * @author Stephen
 *
 */
public interface TypedList<E> 
		extends List<E>, ClassIdentifiedListCommon, ClassIdentifiedElementsStrict {
	
	default public List<E> where(Predicate<E> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		ArrayList<E> listSub = new ArrayList<E>(size());
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			E item = iterator.next();
			if (predicate.test(item)) {
				listSub.add(item);
			}
		}
		listSub.trimToSize();
		return listSub;
	}
}
