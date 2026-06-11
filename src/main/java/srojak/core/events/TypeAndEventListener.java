/**
 * 
 */
package srojak.core.events;

import java.util.EventListener;

/**
 * @author Stephen
 *
 */
public interface TypeAndEventListener {
	Class<?> getListenerClass();
	boolean isOfClass(Class<?> ccmp);
	String getTypeName();
	EventListener getListener();
	<L extends EventListener> L getListenerAs(Class<L> cls);
}
