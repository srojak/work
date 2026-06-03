/**
 * 
 */
package srojak.core.events;

import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public interface CommonEventListenerStore {
	int getListenerCount();
	void clear();
	TypeAndEventListener[] getList();
	<T extends EventListener> List<T> getListeners(Class<T> t);
	<T extends EventListener> void forEach(Class<T> t, Consumer<T> consumer);
	<T extends EventListener> void forEachReversed(Class<T> t, Consumer<T> consumer);
	<T extends EventListener> void add(Class<T> t, T listener);
	<T extends EventListener> void remove(Class<T> t, T listener);
}
