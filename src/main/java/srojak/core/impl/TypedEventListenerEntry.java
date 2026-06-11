/**
 * 
 */
package srojak.core.impl;

import java.util.EventListener;
import java.util.Objects;

import srojak.core.events.TypeAndEventListener;
import srojak.core.events.TypedEventListenerControl;
/**
 * @author Stephen
 *
 */
public class TypedEventListenerEntry
		implements TypeAndEventListener {
	protected final Class<?> _class;
	private final EventListener _listener;

	public TypedEventListenerEntry(Class<?> cls, EventListener listener) {
		Objects.requireNonNull(cls, "cls");
		Objects.requireNonNull(listener, "listener");
		verifyListener(cls, listener);
		_class = cls;
		_listener = listener;
	}
	
	protected void verifyListener(Class<?> cls, EventListener listener) {
		if (TypedEventListenerControl.getRunTimeVerifyTypes()) {
			if (!cls.isInstance(listener)) {
				throw new IllegalArgumentException("listener is not of type "
						+ cls.getTypeName());
			}
		}
	}

	@Override
	public Class<?> getListenerClass() {
		return _class;
	}
	
	@Override
	public boolean isOfClass(Class<?> ccmp) {
		return _class == ccmp;
	}
	
	@Override
	public String getTypeName() {
		return _class.getTypeName();
	}
		
	@Override
	public EventListener getListener() {
		return _listener;
	}

	@Override
	public <L extends EventListener> L getListenerAs(Class<L> cls) {
		Objects.requireNonNull(cls, "cls");
		verifyListener(cls, _listener);
		@SuppressWarnings("unchecked")
		L tls = (L) _listener;
		return tls;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("type ");
		sb.append(_class.getTypeName());
		sb.append(", listener");
		return sb.toString();
	}
}
