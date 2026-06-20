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

import java.util.EventListener;
import java.util.function.Consumer;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;


/**
 * @author Stephen
 *
 */
public abstract class GraphicsControlBase {
	private final CommonEventListenerStore _listeners;
	
	public GraphicsControlBase() {
		_listeners = new CommonEventListenerList();
	}
	
	protected <T extends EventListener> void addListener(Class<T> t, T listener) {
		_listeners.add(t,  listener);
	}
	
	protected <T extends EventListener> void removeListener(Class<T> t, T listener) {
		_listeners.remove(t, listener);
	}
	
	protected <T extends EventListener> void forEachListener(Class<T> t, Consumer<T> consumer) {
		_listeners.forEach(t, consumer);
	}
}
