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

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
/**
 * @author Stephen
 *
 */
public class OwnerReferenceList<T> {
	private LinkedList<OwnedEntry> _list;
	
	public OwnerReferenceList() {
		_list = new LinkedList<OwnedEntry>();
	}
	
	public boolean isEmpty() {
		return _list.isEmpty();
	}
	
	public int size() {
		return _list.size();
	}
	
	public void clear() {
		_list.clear();
	}
	
	public void add(Object owner, T data) {
		OwnedEntry entry = new OwnedEntry(owner, data);
		_list.add(entry);
	}
	
	public boolean remove(Object owner, T data) {
		Objects.requireNonNull(owner, "owner");
		Objects.requireNonNull(data, "data");
		OwnedEntry entry = new OwnedEntry(owner, data);
		return _list.remove(entry);
	}
	
	public boolean removeOwned(Object owner) {
		Objects.requireNonNull(owner, "owner");
		return _list.removeIf(e -> e.isOwnerEqual(owner));
	}
	
	public void forEach(Consumer<? super T> action) {
		for (OwnedEntry entry : _list) {
			action.accept(entry.getData());
		}
	}
	
	public class OwnedEntry {
		private final Object _owner;
		private final T _data;
		
		public OwnedEntry(Object owner, T data) {
			Objects.requireNonNull(owner, "owner");
			Objects.requireNonNull(data, "data");
			
			_owner = owner;
			_data = data;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(_owner, _data);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			@SuppressWarnings("unchecked")
			OwnedEntry other = (OwnedEntry)obj;
			return _owner == other._owner && _data == other._data;
		}

		public boolean isOwnerEqual(Object obj) {
			return _owner == obj;
		}
		
		public T getData() {
			return _data;
		}
	}
}
