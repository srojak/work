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
package srojak.core.events;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.impl.InstanceTypedEventListenerEntry;
import srojak.core.keys.InstanceKey;

/**
 * @author Stephen
 *
 */
public class InstanceKeyedEventListenerList
		implements InstanceKeyedEventListenerStore {
	private final LinkedList<InstanceTypeAndEventListener> _list;
	
	public InstanceKeyedEventListenerList() {
		_list = new LinkedList<InstanceTypeAndEventListener>();
	}

	@Override
	public int getListenerCount() {
		return _list.size();
	}

	@Override
	public void clear() {
		_list.clear();
	}

	@Override
	public List<InstanceTypeAndEventListener> getList() {
		return List.copyOf(_list);
	}

	@Override
	public <T extends EventListener> List<T> getListeners(Class<T> t) {
		ArrayList<T> listWorking = new ArrayList<T>(_list.size());
		ListIterator<InstanceTypeAndEventListener> iter = _list.listIterator();
		while (iter.hasNext()) {
			InstanceTypeAndEventListener entry = iter.next();
			if (entry.isOfClass(t)) {
				T listener = entry.getListenerAs(t);
				listWorking.add(listener);
			}
		}
		listWorking.trimToSize();
		return listWorking;
	}

	@Override
	public <T extends EventListener> void forEach(Class<T> t, Consumer<T> consumer) {
		ListIterator<InstanceTypeAndEventListener> iter = _list.listIterator();
		while (iter.hasNext()) {
			InstanceTypeAndEventListener entry = iter.next();
			if (entry.isOfClass(t)) {
				T listener = entry.getListenerAs(t);
				consumer.accept(listener);
			}
		}
	}

	@Override
	public <T extends EventListener> void forEachReversed(Class<T> t, Consumer<T> consumer) {
		ListIterator<InstanceTypeAndEventListener> iter = _list.listIterator(_list.size());
		while (iter.hasPrevious()) {
			InstanceTypeAndEventListener entry = iter.previous();
			if (entry.isOfClass(t)) {
				T listener = entry.getListenerAs(t);
				consumer.accept(listener);
			}
		}
	}

	@Override
	public synchronized <T extends EventListener> void add(InstanceKey instance, Class<T> t, T listener) {
		if (listener == null) {
			return;
		}
		InstanceTypeAndEventListener entry = new InstanceTypedEventListenerEntry(instance, t, listener);
		_list.add(entry);
	}

	@Override
	public synchronized <T extends EventListener> void remove(InstanceKey instance, Class<T> t, T listener) {
		Objects.requireNonNull(t, "t");
		if (listener == null) {
			return;
		}
		ListIterator<InstanceTypeAndEventListener> iter = _list.listIterator();
		while (iter.hasNext()) {
			InstanceTypeAndEventListener entry = iter.next();
			if (entry.isOfClass(t) && entry.getListener() == listener) {
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void removeForInstance(InstanceKey instance) {
		Objects.requireNonNull(instance, "instance");
		ListIterator<InstanceTypeAndEventListener> iter = _list.listIterator();
		while (iter.hasNext()) {
			InstanceTypeAndEventListener entry = iter.next();
			if (entry.getInstance() == instance) {
				iter.remove();
			}
		}		
	}

}
