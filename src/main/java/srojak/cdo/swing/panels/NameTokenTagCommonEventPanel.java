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
package srojak.cdo.swing.panels;

import java.awt.LayoutManager;

import srojak.core.NameToken;
import srojak.core.events.ActionStatusEvent;
import srojak.core.events.ActionStatusListener;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NameTokenTagCommonEventPanel 
		extends NameTokenTagPanel {
	protected final CommonEventListenerStore _listeners;

	/**
	 * @param tokenName
	 */
	public NameTokenTagCommonEventPanel(NameToken tokenName) {
		super(tokenName);
		_listeners = new CommonEventListenerList();
	}

	/**
	 * @param tokenName
	 * @param layout
	 */
	public NameTokenTagCommonEventPanel(NameToken tokenName, LayoutManager layout) {
		super(tokenName, layout);
		_listeners = new CommonEventListenerList();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public NameTokenTagCommonEventPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_listeners = new CommonEventListenerList();
	}

	/**
	 * @param tokenName
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public NameTokenTagCommonEventPanel(NameToken tokenName, LayoutManager layout, boolean isDoubleBuffered) {
		super(tokenName, layout, isDoubleBuffered);
		_listeners = new CommonEventListenerList();
	}

	protected void sendActionStatus(int idRef, int status) {
		ActionStatusEvent event = new ActionStatusEvent(this, idRef, status);
		_listeners.forEach(ActionStatusListener.class, ls -> ls.statusChanged(event));
	}

	protected void sendStateChange(int idRef, boolean bState) {
		StateChangeEvent event = new StateChangeEvent(this, idRef, bState);
		_listeners.forEach(StateChangeListener.class, ls -> ls.stateChanged(event));
	}
}
