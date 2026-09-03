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
package srojak.cdo.swing.models;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;

import srojak.core.NameToken;
import srojak.core.NameTokenEquatable;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.mantle.decorated.Decorated;
import srojak.mantle.decorated.DecoratedNamed;
import srojak.mantle.decorated.Decorator;

/**
 * @author Stephen
 *
 */
public class DxButtonModelFacade
		extends NameKeyedButtonModelFacade
		implements DecoratedNamed<ButtonModel>, AutoCloseable {
	private final Map<NameToken, Decorator> _decorators;
	private final CommonEventListenerStore _listeners;
	
	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = DxButtonModelFacade.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public DxButtonModelFacade(NameToken name, ButtonModel modelBase) {
		super(name, modelBase);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> getNameIdentfier());
		_listeners = new CommonEventListenerList();
		_decorators = new HashMap<NameToken, Decorator>();
		_model.addActionListener(e -> {
			List<ActionListener> lss = _listeners.getListeners(ActionListener.class);
			for (ActionListener ls : lss) {
				ls.actionPerformed(e);
			}
		});
		_model.addChangeListener(e -> {
			List<ChangeListener> lss = _listeners.getListeners(ChangeListener.class);
			for (ChangeListener ls : lss ) {
				ls.stateChanged(e);
			}
		});
		_model.addItemListener(e -> {
			List<ItemListener> lss = _listeners.getListeners(ItemListener.class);
			for (ItemListener ls : lss ) {
				ls.itemStateChanged(e);
			}
		});
	}

	@Override
	public ButtonModel getValue() {
		return _model;
	}

	@Override
	public boolean bHasDecorator(NameToken tokenKey) {
		return _decorators.containsKey(tokenKey);
	}

	@Override
	public Decorator getDecorator(NameToken tokenKey) {
		return _decorators.get(tokenKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Decorator> D getDecoratorAs(NameToken tokenKey) {
		Decorator db = _decorators.get(tokenKey);
		if (db == null) {
			return null;
		} else {
			return (D) db;
		}
	}

	@Override
	public void putDecorator(Decorator decorator) {
		_decorators.put(decorator.getNameToken(), decorator);		
	}

	@Override
	public boolean isEqualTo(ButtonModel other) {
		return _model == other;
	}

	@Override
	public boolean isEqualTo(Decorated<ButtonModel> other) {
		if (this == other) {
			return true;
		} else if (other == null) {
			return false;
		} else if (other instanceof NameTokenEquatable eqOther) {
			return eqOther.isNameTokenEqual(getNameToken());
		} else {
			return false;
		}
	}

	@Override
	public void setEnabled(boolean b) {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> getNameIdentfier()
				+ " currently " + super.isEnabled() + ", set to " + b);
		super.setEnabled(b);
	}

	@Override
	public void addActionListener(ActionListener l) {
		_listeners.add(ActionListener.class, l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		_listeners.remove(ActionListener.class, l);
	}

	@Override
	public void addItemListener(ItemListener l) {
		_listeners.add(ItemListener.class, l);
	}

	@Override
	public void removeItemListener(ItemListener l) {
		_listeners.remove(ItemListener.class, l);
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		_listeners.add(ChangeListener.class, l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		_listeners.remove(ChangeListener.class, l);
	}

	public void clearListeners() {
		_listeners.clear();
	}

	@Override
	public void close() {
		_listeners.clear();
		_decorators.clear();
	}
}
