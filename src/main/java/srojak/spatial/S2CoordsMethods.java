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
package srojak.spatial;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class S2CoordsMethods {

	public static void visitAround(List<S2Coords> listCoords, Consumer<S2Coords> visitor) {
		Objects.requireNonNull(listCoords, "listCoords");
		Objects.requireNonNull(visitor, "visitor");
		listCoords.forEach(visitor);
	}
	
	public static <T> void visitAroundWith(List<S2Coords> listCoords, T parameter,
			BiConsumer<S2Coords, T> visitor) {
		Objects.requireNonNull(listCoords, "listCoords");
		Objects.requireNonNull(visitor, "visitor");
		listCoords.forEach(c -> visitor.accept(c, parameter));
	}
	
}
