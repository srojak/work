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
package srojak.cdo.swing.collections;

import java.util.Collection;
import java.util.Vector;
import java.util.function.Predicate;

import srojak.core.Labeled;
import srojak.core.tools.CollectionMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class LabeledDataVector<T>
		extends Vector<Labeled<T>> {

	public LabeledDataVector() {
		super();
	}
	
	public LabeledDataVector(Collection<? extends Labeled<T>> collection) {
		super(collection);
	}
	
	public Labeled<T> findFirst(Predicate<Labeled<T>> predicate) {
		return CollectionMethods.findFirstIn(this, predicate);
	}
	
	public int findFirstIndex(Predicate<Labeled<T>> predicate) {
		return CollectionMethods.findFirstIndexIn(this, predicate);
	}
}
