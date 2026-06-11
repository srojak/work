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
package srojak.core.decorated;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import srojak.core.CommonCollectionSize;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public interface DecoratedNamedObjectMapExt<V extends DecoratedNamed<?>>
		extends CommonCollectionSize, Map<NameToken, V> {

	
	default public void overAll(Consumer<V> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		values().forEach(v -> consumer.accept(v));
	}
	
	default public List<V> findAllWhere(Predicate<V> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		LinkedList<V> list = new LinkedList<V>();
		values().forEach(v -> {
			if (predicate.test(v)) {
				list.addLast(v);
			}
		});
		return list;
		
	}
}
