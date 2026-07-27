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
package srojak.cdo.swing.base;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.core.LifeCycleCloseable;
import srojak.core.NameToken;
import srojak.core.decorated.DecoratedNamedObjectMap;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.keys.InstanceKey;
import srojak.core.logic.BooleanLatch;
import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.routed.RoutedStateChangeListener;
import srojak.events.routed.RoutesStateChangeEvents;

/**
 * @author Stephen
 *
 */
public abstract class GuiLifecycleControllerBase
		implements LifeCycleCloseable, RoutesStateChangeEvents {
	protected final CommonEventListenerList _listeners;
	private final InstanceKey _instanceEvent;
	private final DecoratedNamedObjectMap<DxButtonModelFacade> _mapButtonModels;
	private final BooleanLatch _latchWindow;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = GuiLifecycleControllerBase.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public GuiLifecycleControllerBase() {
		_instanceEvent = new InstanceKey(this, "EventOwner");
		_mapButtonModels = new DecoratedNamedObjectMap<DxButtonModelFacade>();
		_listeners = new CommonEventListenerList();
		_latchWindow = new BooleanLatch();
	}
	
	public InstanceKey getEventOwnerInstance() {
		return _instanceEvent;
	}
	
	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(LifeCycleListener.class, listener);
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(LifeCycleListener.class, listener);
	}

	@Override
	public void addRoutedStateChangeListener(RoutedStateChangeListener listener) {
		_listeners.add(RoutedStateChangeListener.class, listener);
	}

	@Override
	public void removeRoutedStateChangeListener(RoutedStateChangeListener listener) {
		_listeners.remove(RoutedStateChangeListener.class, listener);
	}

	public void mergeButtonModels(Collection<DxButtonModelFacade> source) {
		_mapButtonModels.addAll(source);
	}
	
	protected DecoratedNamedObjectMap<DxButtonModelFacade> getButtonModels() {
		return _mapButtonModels;
	}
	
	protected DxButtonModelFacade getButtonModel(NameToken tokenKey) {
		return _mapButtonModels.get(tokenKey);
	}
	
	protected <T extends EventListener> void forEachListener(Class<T> t, Consumer<T> consumer) {
		_listeners.forEach(t, consumer);
	}
	
	public void attachToWindow(Window window) {
		Objects.requireNonNull(window, "window");
		if (_latchWindow.getState()) {
			throw new IllegalStateException("already attached to a window");
		}
		String strWindowClass = window.getClass().getSimpleName();
		_swDebugClass.write(ObsLevel.DEBUG, () -> "attaching to " + strWindowClass);
		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				close();
			}
			
		});
	}
	
	public abstract boolean hasDisplayable();
	
	protected void releaseIncomingListeners() {
		_listeners.clear();
	}
	
	protected abstract void closeSelf();

	@Override
	public void close() {
		String strClassName = getClass().getSimpleName();
		_swDebugClass.write(ObsLevel.INFO, () -> "closing instance of " + strClassName);
		List<LifeCycleListener> list = _listeners.getListeners(LifeCycleListener.class);
		_listeners.clear();
		closeSelf();
		if (!list.isEmpty()) {
			LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_CLOSED);
			list.forEach(ls -> ls.receive(event));
		}
	}
}
