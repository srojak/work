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
import java.util.EventObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Stephen
 *
 */
public interface SingleEventListenerStore<T extends EventListener>
		extends Iterable<T> {
	int getListenerCount();
	void clear();
	List<T> getListeners();
	void forEach(Consumer<? super T> consumer);
	void forEachReversed(Consumer<? super T> consumer);
	<E extends EventObject> void sendToAll(Supplier<E> makeEvent, BiConsumer<T, E> activator);
	<E extends EventObject> void sendToAllReversed(Supplier<E> makeEvent, BiConsumer<T, E> activator);
	void add(T listener);
	void remove(T listener);
}
