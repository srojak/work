/**
 * 
 */
package srojak.core.events;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.impl.TypedEventListenerEntry;

/**
 * @author Stephen
 *
 */
public class CommonEventListenerList
		implements CommonEventListenerStore {
	private final LinkedList<TypedEventListenerEntry> _list;
	
	public CommonEventListenerList() {
		_list = new LinkedList<TypedEventListenerEntry>();
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
	public List<TypeAndEventListener> getList() {
		return List.copyOf(_list);
	}

	@Override
	public <T extends EventListener> List<T> getListeners(Class<T> t) {
		ArrayList<T> listWorking = new ArrayList<T>(_list.size());
		ListIterator<TypedEventListenerEntry> iter = _list.listIterator();
		while (iter.hasNext()) {
			TypedEventListenerEntry pair = iter.next();
			if (pair.isOfClass(t)) {
				T listener = pair.getListenerAs(t);
				listWorking.add(listener);
			}
		}
		listWorking.trimToSize();
		return listWorking;
	}

	@Override
	public <T extends EventListener> void forEach(Class<T> t, Consumer<T> consumer) {
		ListIterator<TypedEventListenerEntry> iter = _list.listIterator();
		while (iter.hasNext()) {
			TypedEventListenerEntry entry = iter.next();
			if (entry.isOfClass(t)) {
				T listener = entry.getListenerAs(t);
				consumer.accept(listener);
			}
		}
	}

	@Override
	public <T extends EventListener> void forEachReversed(Class<T> t, Consumer<T> consumer) {
		ListIterator<TypedEventListenerEntry> iter = _list.listIterator(_list.size());
		while (iter.hasPrevious()) {
			TypedEventListenerEntry entry = iter.previous();
			if (entry.isOfClass(t)) {
				T listener = entry.getListenerAs(t);
				consumer.accept(listener);
			}
		}
	}

	@Override
	public synchronized <T extends EventListener> void add(Class<T> t, T listener) {
		if (listener == null) {
			return;
		}
		TypedEventListenerEntry entry = new TypedEventListenerEntry(t, listener);
		_list.add(entry);
	}

	@Override
	public synchronized <T extends EventListener> void remove(Class<T> t, T listener) {
		Objects.requireNonNull(t, "t");
		if (listener == null) {
			return;
		}
		ListIterator<TypedEventListenerEntry> iter = _list.listIterator();
		while (iter.hasNext()) {
			TypedEventListenerEntry pair = iter.next();
			if (pair.isOfClass(t) && pair.getListener() == listener) {
				iter.remove();
			}
		}
	}
	
	@Override
	public String toString() {
		List<TypedEventListenerEntry> listWorking = List.copyOf(_list);
		StringBuilder sb = new StringBuilder("CommonEventListenerArray");
		sb.append(" size=");
		sb.append(listWorking.size());
		ListIterator<TypedEventListenerEntry> iterator = listWorking.listIterator();
		while (iterator.hasNext()) {
			TypedEventListenerEntry pair = iterator.next();
			sb.append("\n  ");
			sb.append(pair);
		}
		return sb.toString();
	}
}
