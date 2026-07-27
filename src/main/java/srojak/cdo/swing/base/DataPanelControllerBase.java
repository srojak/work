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

import java.util.List;

import srojak.core.LifeCycleCloseable;
import srojak.core.NameToken;
import srojak.core.NameTokenEquatable;
import srojak.core.NameTokenKeyedBase;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.CommandEvent;
import srojak.events.CommandEventOriginator;
import srojak.events.CommandListener;

/**
 * @author Stephen
 *
 * still interested in this because of the life cycle implementation
 */
@Deprecated
public abstract class DataPanelControllerBase
		extends NameTokenKeyedBase
		implements LifeCycleCloseable, CommandEventOriginator {
	protected final CommonEventListenerStore _listeners;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = DataPanelControllerBase.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param tokenKey
	 */
	public DataPanelControllerBase(NameToken tokenKey) {
		super(tokenKey);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "key = " + tokenKey.getName());
		_listeners = new CommonEventListenerList();
	}

	@Override
	protected boolean isComparable(NameTokenEquatable other) {
		return other instanceof DataPanelControllerBase;
	}

	protected void sendCommandEvent(CommandEvent event) {
		_listeners.forEach(CommandListener.class, ls -> ls.execute(event));
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
	public void addCommandListener(CommandListener listener) {
		_listeners.add(CommandListener.class, listener);
	}

	@Override
	public void removeCommandListener(CommandListener listener) {
		_listeners.remove(CommandListener.class, listener);
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
