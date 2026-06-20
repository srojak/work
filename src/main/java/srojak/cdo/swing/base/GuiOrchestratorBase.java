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

import java.util.Collection;
import java.util.function.Consumer;

import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.core.NameToken;
import srojak.core.decorated.DecoratedNamedObjectMap;
import srojak.core.events.InstanceKeyedEventListenerList;
import srojak.core.events.InstanceKeyedEventListenerStore;
import srojak.core.events.InstanceListenerBearing;
import srojak.core.keys.InstanceKey;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.CommandListener;
import srojak.events.InstanceCommandEventOriginator;

/**
 * @author Stephen
 *
 */
public abstract class GuiOrchestratorBase
		implements InstanceListenerBearing, InstanceCommandEventOriginator {
	private final DecoratedNamedObjectMap<DxButtonModelFacade> _mapButtonModels;
	protected final InstanceKeyedEventListenerStore _listeners;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = GuiOrchestratorBase.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public GuiOrchestratorBase() {
		_mapButtonModels = new DecoratedNamedObjectMap<DxButtonModelFacade>();
		_listeners = new InstanceKeyedEventListenerList();
	}
	
	protected void addedButtonModel(DxButtonModelFacade facade) {
		// base class method does nothing
	}
	
	public void mergeButtonModels(Collection<DxButtonModelFacade> source) {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM,
				() -> "source has " + source.size() + " elements");
		for (DxButtonModelFacade item : source) {
			_mapButtonModels.add(item);
			addedButtonModel(item);
		}
	}
	
	protected DecoratedNamedObjectMap<DxButtonModelFacade> getButtonModels() {
		return _mapButtonModels;
	}
	
	protected DxButtonModelFacade getButtonModel(NameToken tokenKey) {
		return _mapButtonModels.get(tokenKey);
	}
	
	protected void overButtonModels(Consumer<DxButtonModelFacade> consumer) {
		_mapButtonModels.overAll(consumer);
	}
	
	public abstract void initialize();
	
	public abstract void sync();

	@Override
	public void removeListeners(InstanceKey instance) {
		_listeners.removeForInstance(instance);
	}

	@Override
	public void addCommandListener(InstanceKey instance, CommandListener listener) {
		_listeners.add(instance, CommandListener.class, listener);
	}

	@Override
	public void removeCommandListener(InstanceKey instance, CommandListener listener) {
		_listeners.remove(instance, CommandListener.class, listener);
	}
}
