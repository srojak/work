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
package srojak.core.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import srojak.core.ActiveAndRunnable;
import srojak.core.CommonCollectionSize;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ActiveAndRunnableList 
		extends LinkedList<ActiveAndRunnable> 
		implements CommonCollectionSize {
	
	public ActiveAndRunnableList() {
		super();
	}
	
	public void traverse(Consumer<ActiveAndRunnable> consumer) {
		Iterator<ActiveAndRunnable> iterator = this.iterator();
		while (iterator.hasNext()) {
			ActiveAndRunnable task = iterator.next();
			if (!task.isActive()) {
				iterator.remove();
			} else {
				consumer.accept(task);
			}
		}
	}
}
