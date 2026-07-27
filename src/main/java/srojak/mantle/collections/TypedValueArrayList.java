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
import java.util.Collection;
import java.util.Objects;

import srojak.core.reflect.TypeBearingValue;

/**
 * @author Stephen
 *
 */
public final class TypedValueArrayList<E>
		extends TypedValueListBase<E> {

	public TypedValueArrayList() {
		super(new ArrayList<TypeBearingValue<E>>());
	}
	
	public TypedValueArrayList(Collection<? extends E> c) {
		super(new ArrayList<TypeBearingValue<E>>());
		Objects.requireNonNull(c, "c");
		for (E value : c) {
			TypeBearingValue<E> entryNew = new TypeBearingValue<E>(value);
			_list.add(entryNew);
		}
	}
}
