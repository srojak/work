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
package srojak.cdo;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import srojak.core.GPoly;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class PointPolyList
		extends LinkedList<Point> 
		implements GPoly {

	/**
	 * 
	 */
	public PointPolyList() {
		super();
	}

	/**
	 * @param c
	 */
	public PointPolyList(Collection<? extends Point> c) {
		super(c);
	}

	@Override
	public int[] getArrayOfX() {
		int[] array = new int[size()];
		int index = 0;
		ListIterator<Point> iterator = listIterator();
		while (iterator.hasNext()) {
			array[index++] = iterator.next().x;
		}
		return array;
	}

	@Override
	public int[] getArrayOfY() {
		int[] array = new int[size()];
		int index = 0;
		ListIterator<Point> iterator = listIterator();
		while (iterator.hasNext()) {
			array[index++] = iterator.next().y;
		}
		return array;
	}

}
