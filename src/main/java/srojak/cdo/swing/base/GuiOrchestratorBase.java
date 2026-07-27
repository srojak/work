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
import java.util.Objects;
import java.util.function.Consumer;

import srojak.cdo.swing.DataComponent;
import srojak.cdo.swing.collections.ButtonModelFacadeMap;
import srojak.cdo.swing.functional.OrchReceptor;
import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.core.NameToken;
import srojak.core.events.InstanceKeyedEventListenerList;
import srojak.core.events.InstanceKeyedEventListenerStore;
import srojak.core.events.InstanceListenerBearing;
import srojak.core.keys.InstanceKey;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugSwitch;
import srojak.events.CommandListener;
import srojak.events.InstanceCommandEventOriginator;

/**
 * @author Stephen
 *
 */
public abstract class GuiOrchestratorBase
		implements InstanceListenerBearing, InstanceCommandEventOriginator {
	protected final ButtonModelFacadeMap _mapButtonModels;
	protected final InstanceKeyedEventListenerStore _listeners;
	private final DebugSwitch _swDebugClass;
	
	public GuiOrchestratorBase(DebugSwitch swDebug) {
		Objects.requireNonNull(swDebug, "swDebug");
		_swDebugClass = swDebug;
		_mapButtonModels = new ButtonModelFacadeMap();
		_listeners = new InstanceKeyedEventListenerList();
	}
	
	protected <C extends DataComponent> OrchReceptor<C> createReceptor(Class<C> classComponent) {
		Objects.requireNonNull(classComponent, "classComponent");
		return new OrchReceptor<C>(classComponent, _mapButtonModels);
	}
	
	public ButtonModelFacadeMap getButtonModels() {
		return _mapButtonModels;
	}
	
	protected boolean containsButtonModel(NameToken tokenKey) {
		return _mapButtonModels.containsKey(tokenKey);
	}
	
	protected DxButtonModelFacade getButtonModel(NameToken tokenKey) {
		DxButtonModelFacade facade = _mapButtonModels.get(tokenKey);
		if (facade == null) {
			_swDebugClass.write(ObsLevel.ERROR, () -> "did not find model for " + tokenKey);
		}
		return facade;
	}
	
	public void mergeButtonModels(Collection<DxButtonModelFacade> source) {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM,
				() -> "source has " + source.size() + " elements");
		_mapButtonModels.addAll(source);
	}
	
	protected void overButtonModels(Consumer<DxButtonModelFacade> consumer) {
		_mapButtonModels.overAll(consumer);
	}
	
	public abstract void receiveDataComponent(DataComponent dc);
	
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
