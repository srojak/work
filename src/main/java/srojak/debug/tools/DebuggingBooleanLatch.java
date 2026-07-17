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
package srojak.debug.tools;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenBearing;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;
import srojak.core.logic.BooleanLatch;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class DebuggingBooleanLatch
		extends BooleanLatch
		implements NameTokenBearing {
	private final NameToken _name;

	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = DebuggingBooleanLatch.class;	
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis), ObsLevel.LOWEST, false);
	}
	
	/**
	 * 
	 */
	public DebuggingBooleanLatch(NameToken tokenName) {
		super();
		Objects.requireNonNull(tokenName, "tokenName");
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "name = " + tokenName.getName());
		_name = tokenName;
		addStateChangeListener(new DebugStateChangeListener());
	}

	/**
	 * @param bStateInitial
	 */
	public DebuggingBooleanLatch(NameToken tokenName, boolean bStateInitial) {
		super(bStateInitial);
		Objects.requireNonNull(tokenName, "tokenName");
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "name = " + tokenName.getName()
				+ ", initial state = " + bStateInitial);
		_name = tokenName;
		addStateChangeListener(new DebugStateChangeListener());
	}

	@Override
	public NameToken getNameToken() {
		return _name;
	}

	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return _name.equals(token);
	}
	
	@Override
	public void propagate() {
		_swDebugClass.writeTraceEnter(TraceLevel.LOW, () ->  "name = " + _name.getName());
		super.propagate();
	}

	@Override
	public boolean setState(boolean bState) {
		_swDebugClass.writeTraceEnter(TraceLevel.LOW, () ->  "name = " + _name.getName()
				+ ", set state to " + bState);
		return super.setState(bState);
	}

	private class DebugStateChangeListener
			implements StateChangeListener {
		@Override
		public void stateChanged(StateChangeEvent event) {
			_swDebugClass.write(ObsLevel.DEBUG3, "name = " + _name.getName()
			+ ", event = " + event.toDataString());
		}
	}
}
