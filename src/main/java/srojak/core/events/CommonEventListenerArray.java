/**
 * 
 */
package srojak.core.events;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.impl.TypedEventListenerEntry;

/**
 * @author Stephen
 *
 */
public class CommonEventListenerArray 
		implements CommonEventListenerStore, Serializable {
	// try it their way
	private transient volatile TypedEventListenerEntry[] _list;
    private static final TypedEventListenerEntry[] NULL_ARRAY;
	private static final long serialVersionUID = -8186362829543763971L;
    
    static {
    	NULL_ARRAY = new TypedEventListenerEntry[0];
    }

	/**
	 * 
	 */
	public CommonEventListenerArray() {
		_list = NULL_ARRAY;
	}

	@Override
	public int getListenerCount() {
		return _list.length;
	}
	
	@Override
	public void clear() {
		_list = NULL_ARRAY;
	}

	@Override
	public List<TypeAndEventListener> getList() {		
		return List.of(_list);
	}
	
	private static int getCountOfTyped(TypedEventListenerEntry[] list, Class<?> t) {
		int count = 0;
		for (TypedEventListenerEntry pair : list) {
			if (pair.isOfClass(t)) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public <T extends EventListener> List<T> getListeners(Class<T> t) {
		TypedEventListenerEntry[] listWorking = _list;
		int nSize = getCountOfTyped(listWorking, t);
		ArrayList<T> array = new ArrayList<T>(nSize);
		for (TypedEventListenerEntry pair : listWorking) {
			if (pair.isOfClass(t)) {
				T listener = pair.getListenerAs(t);
				array.add(listener);
			}
		}
		return array;
	}

	@Override
	public <T extends EventListener> void forEach(Class<T> t, Consumer<T> consumer) {
		List<T> listeners = getListeners(t);
		ListIterator<T> iter = listeners.listIterator();
		while (iter.hasNext()) {
			consumer.accept(iter.next());
		}
	}
	
	@Override
	public <T extends EventListener> void forEachReversed(Class<T> t, Consumer<T> consumer) {
		List<T> listeners = getListeners(t);
		ListIterator<T> iter = listeners.listIterator(listeners.size());
		while (iter.hasPrevious()) {
			consumer.accept(iter.previous());
		}
	}
	
	@Override
	public synchronized <T extends EventListener> void add(Class<T> t, T listener) {
		if (listener == null) {
			return;
		}
		TypedEventListenerEntry pair = new TypedEventListenerEntry(t, listener);
		if (_list == NULL_ARRAY) {
			_list = new TypedEventListenerEntry[1];
			_list[0] = pair;
		} else {
			int idxNew = _list.length;
			TypedEventListenerEntry[] listNew = new TypedEventListenerEntry[_list.length + 1];
			System.arraycopy(_list, 0, listNew, 0, _list.length);
			listNew[idxNew] = pair;
			_list = listNew;
		}
	}
	
	@Override
	public synchronized <T extends EventListener> void remove(Class<T> t, T listener) {
		Objects.requireNonNull(t, "t");
		if (listener == null) {
			return;
		}
		int idxRemove = -1;
		for (int idx = _list.length - 1; idx >= 0; idx--) {
			if (_list[idx].isOfClass(t) && _list[idx].getListener() == listener) {
				idxRemove = idx;
				break;
			}
		}
		if (idxRemove >= 0) {
			if (_list.length == 1) {
				// only one on the list
				_list = NULL_ARRAY;
			} else {
				TypedEventListenerEntry[] listNew = new TypedEventListenerEntry[_list.length - 1];
				System.arraycopy(_list, 0, listNew, 0, idxRemove);
				if (idxRemove < listNew.length) {
					System.arraycopy(_list, idxRemove + 1, listNew, idxRemove,
							listNew.length - idxRemove);
				}
				_list = listNew;
			}
		}
	}
	
	@Override
	public String toString() {
		TypedEventListenerEntry[] listWorking = _list;
		StringBuilder sb = new StringBuilder("CommonEventListenerArray");
		sb.append(" length=");
		sb.append(listWorking.length);
		for (TypedEventListenerEntry pair : listWorking) {
			sb.append("\n  ");
			sb.append(pair);
		}
		return sb.toString();
	}
	
    @Serial
    private void writeObject(ObjectOutputStream s)
    		throws IOException {
		TypedEventListenerEntry[] listWorking = _list;
		s.defaultWriteObject();
		for (TypedEventListenerEntry pair : listWorking) {
			EventListener listener = pair.getListener();
			if (listener instanceof Serializable) {
				s.writeObject(pair.getTypeName());
				s.writeObject(listener);
			}
		}
		s.writeObject(null);
    }

    @Serial
    private void readObject(ObjectInputStream s) 
    		throws IOException, ClassNotFoundException {
    	ArrayList<TypedEventListenerEntry> alist
    		= new ArrayList<TypedEventListenerEntry>();
    	s.defaultReadObject();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	Object objListenerType;
    	
    	while ((objListenerType = s.readObject()) != null) {
    		String strName = (String) objListenerType;
    		EventListener listener = (EventListener)s.readObject();
    		Class<?> citem = Class.forName(strName, true, loader);
    		TypedEventListenerEntry item
    			 = new TypedEventListenerEntry(citem, listener);
    		alist.add(item);
    	}
    	
    	if (alist.isEmpty()) {
    		_list = NULL_ARRAY;
    	} else {
    		TypedEventListenerEntry[] listWorking
    				= alist.toArray(new TypedEventListenerEntry[alist.size()]);
    		_list = listWorking;
    	}
    }
}
