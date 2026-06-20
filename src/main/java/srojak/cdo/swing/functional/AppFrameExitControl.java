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
package srojak.cdo.swing.functional;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import srojak.cdo.swing.ExitControl;
import srojak.core.events.CommonEventListenerArray;
import srojak.events.CancellableEvent;
import srojak.events.CancellableEventListener;
/**
 * @author Stephen
 *
 */
public class AppFrameExitControl
		implements ExitControl {
	private CommonEventListenerArray _listeners;
	
	public AppFrameExitControl() {
		_listeners = new CommonEventListenerArray();
	}

	@Override
	public void addClosingListener(CancellableEventListener listener) {
		_listeners.add(CancellableEventListener.class, listener);
	}
	
	@Override
	public void removeClosingListener(CancellableEventListener listener) {
		_listeners.remove(CancellableEventListener.class, listener);
	}
	
	private boolean tryClosing() {
		CancellableEvent event = new CancellableEvent(this);
		_listeners.forEach(CancellableEventListener.class, ls -> ls.initiated(event));
		return !event.isCancelled();
	}
	
	public void attach(JFrame frameMain) {
		frameMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frameMain.addWindowListener(new MainFrameWindowAdapter());
	}
	
	@Override
	public void requestClose() {
		if (tryClosing()) {
			System.exit(0);
		}
	}
	
	private class MainFrameWindowAdapter
			extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			if (tryClosing()) {
				System.exit(0);
			}
		}
		
	}
}
