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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.ActionCommandBearing;
import srojak.cdo.ActionEventOriginator;
import srojak.cdo.events.AWTEventMethods;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.core.NameToken;
import srojak.core.NameTokenTagged;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.logic.FlagsInt;

/**
 * @author Stephen
 *
 */
public class ActionControlModelBase 
		implements NameTokenTagged, ActionCommandBearing, ActionEventOriginator, ChangeEventOriginator {
	protected final NameToken _token;
	protected final CommonEventListenerStore _listeners;
	protected final FlagsInt _flags;
	protected String _cmdAction;
	
	protected static final int ENABLED = 0x1;
	
	public ActionControlModelBase(NameToken tokenName) {
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_listeners = new CommonEventListenerList();
		_flags = new FlagsInt();
		_cmdAction = "";
	}

	@Override
	public NameToken getNameTag() {
		return _token;
	}

	@Override
	public boolean isNameTagEqual(NameToken token) {
		return _token.equals(token);
	}

	@Override
	public boolean isNameTagEqual(String strName) {
		return _token.isNameEqual(strName);
	}

	@Override
	public String getActionCommand() {
		return _cmdAction;
	}
	
	@Override
	public void setActionCommand(String s) {
		if (s == null) {
			_cmdAction = "";
		} else {
			_cmdAction = s;
		}
	}

	public boolean isEnabled() {
		return _flags.test(ENABLED);
	}
	
	public void setEnabled(boolean bState) {
		if (_flags.test(ENABLED) != bState) {
			if (bState) {
				_flags.set(ENABLED);
			} else {
				_flags.clear(ENABLED);
			}
			ChangeEvent event = new ChangeEvent(this);
			_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(event));
		}
	}

	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		_listeners.remove(ChangeListener.class, listener);
	}

	@Override
	public void addActionListener(ActionListener listener) {
		_listeners.add(ActionListener.class, listener);
	}

	@Override
	public void removeActionListener(ActionListener listener) {
		_listeners.remove(ActionListener.class, listener);
	}
	
	public void relayActionEvent(ActionEvent event) {
		ActionEvent event2 = AWTEventMethods.copyActionEvent(this, event);
		_listeners.forEach(ActionListener.class, ls -> ls.actionPerformed(event2));
	}
}
