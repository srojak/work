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
package srojak.sql.mapobj;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntBiFunction;

/**
 * @author Stephen
 *
 */
public class MappedObjectFunctionComparator<M extends AbstractMappedObject> 
		implements Comparator<M> {
	private final ToIntBiFunction<M, M> _fnCompare;
	
	public MappedObjectFunctionComparator(ToIntBiFunction<M, M> function) {
		Objects.requireNonNull(function, "function");
		_fnCompare = function;
	}

	@Override
	public int compare(M o1, M o2) {
		Objects.requireNonNull(o1, "o1");
		Objects.requireNonNull(o2, "o2");
		return _fnCompare.applyAsInt(o1, o2);
	}

}
