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
package srojak.core.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Stephen
 *
 */
public class StreamMethods {

	public static <T> Set<T> toSet(Stream<T> stream) {
		HashSet<T> set = new HashSet<T>();
		set.addAll(stream.toList());
		return set;
	}
	
	public static <T> Set<T> toSet(Stream<T> stream, int szInitial) {
		HashSet<T> set = new HashSet<T>(szInitial);
		set.addAll(stream.toList());
		return set;
	}
}
