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
package srojak.core.events;

import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

import srojak.core.keys.InstanceKey;

/**
 * @author Stephen
 *
 */
public interface InstanceKeyedEventListenerStore {
	int getListenerCount();
	void clear();
	List<InstanceTypeAndEventListener> getList();
	<T extends EventListener> List<T> getListeners(Class<T> t);
	<T extends EventListener> void forEach(Class<T> t, Consumer<T> consumer);
	<T extends EventListener> void forEachReversed(Class<T> t, Consumer<T> consumer);
	<T extends EventListener> void add(InstanceKey instance, Class<T> t, T listener);
	<T extends EventListener> void remove(InstanceKey instance, Class<T> t, T listener);
	void removeForInstance(InstanceKey instance);
}
