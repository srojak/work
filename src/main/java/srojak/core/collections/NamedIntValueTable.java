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
package srojak.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import srojak.core.NamedIntValue;

/**
 * @author Stephen
 *
 */
public class NamedIntValueTable
				implements Collection<NamedIntValue> {
	private final HashMap<String, NamedIntValue> _table;
	
	public NamedIntValueTable() {
		_table = new HashMap<String, NamedIntValue>();
	}

	@Override
	public int size() {
		return _table.size();
	}

	@Override
	public boolean isEmpty() {
		return _table.isEmpty();
	}

	@Override
	public void clear() {
		_table.clear();
	}
	
	public boolean containsKey(String strKey) {
		return _table.containsKey(strKey);
	}

	@Override
	public boolean contains(Object o) {
		return _table.containsValue(o);
	}
	
	public NamedIntValue get(String strKey) {
		return _table.get(strKey);
	}

	@Override
	public Iterator<NamedIntValue> iterator() {
		return _table.values().iterator();
	}

	@Override
	public Object[] toArray() {
		return _table.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return _table.values().toArray(a);
	}

	@Override
	public boolean add(NamedIntValue e) {
		Objects.requireNonNull(e, "e");
		_table.put(e.getName(), e);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o != null) {
			if (o instanceof NamedIntValue item) {
				return _table.remove(item.getName(), item);
			}
		}
		return false;
	}
	
	public boolean removeByKey(String strKey) {
		NamedIntValue item = _table.remove(strKey);
		return item != null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return _table.values().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends NamedIntValue> c) {
		boolean bResult = false;
		for (NamedIntValue item : c) {
			_table.put(item.getName(), item);
			bResult = true;
		}
		return bResult;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean bResult = false;
		ArrayList<NamedIntValue> listWork = new ArrayList<NamedIntValue>();
		for (NamedIntValue item : _table.values()) {
			if (c.contains(item)) {
				listWork.add(item);
				bResult = true;
			}
		}
		listWork.forEach(i -> _table.remove(i.getName()));
		return bResult;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean bResult = false;
		ArrayList<NamedIntValue> listWork = new ArrayList<NamedIntValue>();
		for (NamedIntValue item : _table.values()) {
			if (!c.contains(item)) {
				listWork.add(item);
				bResult = true;
			}
		}
		listWork.forEach(i -> _table.remove(i.getName()));
		return bResult;
	}
}
