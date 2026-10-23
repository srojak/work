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
package srojak.core.observe.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObservationWriter;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ObservationWritersUniqueList 
		extends LinkedList<ObservationWriter> {

	/**
	 * 
	 */
	public ObservationWritersUniqueList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void merge(List<ObservationWriter> list) {
		Objects.requireNonNull(list, "list");
		Iterator<ObservationWriter> iterator = list.iterator();
		while (iterator.hasNext()) {
			ObservationWriter writer = iterator.next();
			if (!contains(writer)) {
				super.add(writer);
			}
		}
	}

	@Override
	public boolean add(ObservationWriter e) {
		Objects.requireNonNull(e, "e");
		if (!contains(e)) {
			return super.add(e);
		} else {
			return false;
		}
	}

	@Override
	public void add(int index, ObservationWriter element) {
		Objects.requireNonNull(element, "element");
		if (!contains(element)) {
			super.add(index, element);
		}
	}
}
