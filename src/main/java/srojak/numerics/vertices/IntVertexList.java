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
package srojak.numerics.vertices;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

import srojak.core.GPoly;

/**
 * @author Stephen
 *
 */
public class IntVertexList
		implements GPoly {
	private final LinkedList<IntVertex> _list;
	
	/**
	 * 
	 */
	public IntVertexList() {
		_list = new LinkedList<IntVertex>();
	}
	
	public void add(int x, int y) {
		_list.addLast(new IntVertex(x, y));
	}
	
	public void add(IntVertex vertex) {
		Objects.requireNonNull(vertex, "vertex");
		_list.addLast(vertex);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}
	
	public IntVertexList scale(int nScale) {
		IntVertexList listScaled = new IntVertexList();
		for (IntVertex vertex : _list) {
			listScaled.add(new IntVertex(nScale * vertex._x, nScale * vertex._y));
		}
		return listScaled;
	}
	
	@Override
	public int[] getArrayOfX() {
		int[] array = new int[_list.size()];
		int index = 0;
		ListIterator<IntVertex> iterator = _list.listIterator();
		while (iterator.hasNext()) {
			array[index++] = iterator.next()._x;
		}
		return array;
	}
	
	@Override
	public int[] getArrayOfY() {
		int[] array = new int[_list.size()];
		int index = 0;
		ListIterator<IntVertex> iterator = _list.listIterator();
		while (iterator.hasNext()) {
			array[index++] = iterator.next()._y;
		}
		return array;
		
	}
}
