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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import srojak.numerics.SlopeAndIntercept;

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
	
	public static void applySuccessively(Collection<S2Coords> coords, 
			BiConsumer<S2Coords, S2Coords> consumer) {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(consumer, "consumer");
		Iterator<S2Coords> iterator = coords.iterator();
		S2Coords coordsFrom = null;
		if (iterator.hasNext()) {
			coordsFrom = iterator.next();
		}
		while (iterator.hasNext()) {
			S2Coords coordsTo = iterator.next();
			consumer.accept(coordsFrom, coordsTo);
			coordsFrom = coordsTo;
		}
	}
	
	public static SlopeAndIntercept getSlopeAndIntercept(S2Coords coordsFrom, S2Coords coordsTo) {
		Objects.requireNonNull(coordsFrom, "coordsFrom");
		Objects.requireNonNull(coordsTo, "coordsTo");
		double m = ((double) (coordsTo._y - coordsFrom._y))
				/ ((double) (coordsTo._x - coordsFrom._x));
		double b = coordsTo._y - m * coordsTo._x;
		return new SlopeAndIntercept(m, b);
	}
	
	public static List<S2Segment> getSegmentsFrom(Collection<S2Coords> coords) {
		Objects.requireNonNull(coords, "coords");
		int nCoords = coords.size();
		ArrayList<S2Segment> list = new ArrayList<S2Segment>(nCoords > 1 ? nCoords - 1 : 0);
		Iterator<S2Coords> iterator = coords.iterator();
		S2Coords coordsFrom = null;
		if (iterator.hasNext()) {
			coordsFrom = iterator.next();
		}
		while (iterator.hasNext()) {
			S2Coords coordsTo = iterator.next();
			S2Segment segment = new S2Segment(coordsFrom, coordsTo, true);
			list.add(segment);
			coordsFrom = coordsTo;
		}
		return list;
	}
}
