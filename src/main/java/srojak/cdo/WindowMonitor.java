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
package srojak.cdo;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenBearing;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;

/**
 * @author Stephen
 *
 * Monitor the existence of a window.
 */
public class WindowMonitor
		implements NameTokenBearing {
	private final NameToken _token;
	private final SingleEventListenerStore<StateChangeListener> _listeners;
	private Window _window;
	
	/**
	 * Constructor.
	 * @param tokenName The identifying {@code NameToken} for the monitor.
	 */
	public WindowMonitor(NameToken tokenName) {
		Objects.requireNonNull(tokenName);
		_token = tokenName;
		_listeners = new SingleEventListenerList<StateChangeListener>();
		_window = null;
	}
	
	/**
	 * Get the identifying {@code NameToken} for the object.
	 * @return The {@code NameToken} identifying the object.
	 */
	@Override
	public NameToken getNameToken() {
		return _token;
	}

	/**
	 * Is the  identifying {@code NameToken} for the object equal to the given {@code NameToken}?
	 * @param token The value to which to compare for equality.
	 * @return {@code true} if the {@code NameToken} values are equal.
	 */
	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return _token.equals(token);
	}
	
	public void addStateChangeListener(StateChangeListener listener) {
		_listeners.add(listener);
	}
	
	public void removeStateChangeListener(StateChangeListener listener) {
		_listeners.remove(listener);
	}

	/**
	 * Assign a window to the monitor.
	 * @param window The window that the monitor will observe.
	 * @throws IllegalStateException If the monitor is observing another window.
	 */
	public void assignWindow(Window window) {
		Objects.requireNonNull(window, "window");
		if (_window != null) {
			throw new IllegalStateException("another window is open");
		}
		_window = window;
		StateChangeEvent event = new StateChangeEvent(this, true);
		_listeners.forEach(ls -> ls.stateChanged(event));
		window.addWindowListener(new MonitorWindowListener());
	}
	
	private void releaseWindow() {
		_window = null;
		StateChangeEvent event = new StateChangeEvent(this, false);
		_listeners.forEach(ls -> ls.stateChanged(event));
	}
	
	private class MonitorWindowListener
			extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			releaseWindow();
		}
	}
}
