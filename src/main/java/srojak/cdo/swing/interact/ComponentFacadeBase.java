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
package srojak.cdo.swing.interact;

import java.util.Objects;

import javax.swing.JComponent;

import srojak.cdo.swing.ComponentFacadeFlags;
import srojak.core.NameToken;
import srojak.core.NameTokenTagged;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public abstract class ComponentFacadeBase
		implements ComponentFacadeFlags  {
	protected final CommonEventListenerStore _listeners;
	protected final ClassReflector _reflector;
	private final JComponent _component;
	private final FlagsInt _flags;
	
	/**
	 * 
	 */
	public ComponentFacadeBase(JComponent component) {
		Objects.requireNonNull(component, "component");
		_listeners = new CommonEventListenerList();
		_component = component;
		_reflector = new ClassReflector(_component);
		_flags = new FlagsInt();
		_flags.set(CFF_PARENT_ENABLED, CFF_LOCAL_ENABLED);
		if (_component.isEnabled()) {
			_flags.set(CFF_MODEL_ENABLED);
		}
	}

	public JComponent getComponent() {
		return _component;
	}
	
	public <C extends JComponent> C getComponentAs() {
		@SuppressWarnings("unchecked")
		C ctyped = (C) _component;
		return ctyped;
	}
	
	public FlagsIntTest getFlags() {
		return _flags;
	}
	
	public String getName() {
		return _component.getName();
	}
	
	public boolean isEnabled() {
		return _flags.testAnd(CFF_MODEL_ENABLED, CFF_LOCAL_ENABLED, CFF_PARENT_ENABLED);
	}
	
	public boolean isModelEnabled() {
		return _flags.test(CFF_MODEL_ENABLED);
	}
	
	protected abstract void changeComponentEnabled();
	
	protected void setEnabledImmediate(boolean bState) {
		_flags.apply(bState, CFF_MODEL_ENABLED);
	}
	
	public void setEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_MODEL_ENABLED)) {
			changeComponentEnabled();
		}
	}
	
	public boolean isParentEnabled() {
		return _flags.test(CFF_PARENT_ENABLED);
	}
	
	public void setParentEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_LOCAL_ENABLED)) {
			changeComponentEnabled();
		}
	}
	
	public boolean isLocalEnabled() {
		return _flags.test(CFF_PARENT_ENABLED);
	}
	
	public void setLocalEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_LOCAL_ENABLED)) {
			changeComponentEnabled();
		}
	}
	
	public boolean hasNameToken() {
		return _component instanceof NameTokenTagged;
	}
	
	public NameToken getNameToken() {
		if (_component instanceof NameTokenTagged ntb) {
			return ntb.getNameTag();
		} else {
			return null;
		}
	}
}
