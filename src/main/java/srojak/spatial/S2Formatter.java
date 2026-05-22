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

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class S2Formatter {

	public static String formatListOfCoords(String strName, int nPointsPerLine,
			EnumSet<S2Formats> formatCoords, Collection<S2Coords> collection) {
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(collection, "collection");
		if (nPointsPerLine <= 0) {
			throw new IllegalArgumentException("nPointsPerLine must be positive");
		}
		StringBuilder sb = new StringBuilder(strName);
		sb.append(" [ ");
		int n = 1; // this reduces the number of items on the first line
		Iterator<S2Coords> iter = collection.iterator();
		if (iter.hasNext()) {
			sb.append(iter.next().toString(formatCoords));		
		}
		while (iter.hasNext()) {
			sb.append(',');
			if (++n >= nPointsPerLine) {
				sb.append("\n    ");
				n = 0;
			} else {
				sb.append(' ');
			}
			sb.append(iter.next().toString(formatCoords));
		}
		sb.append(" ]");
		return sb.toString();
	}
}
