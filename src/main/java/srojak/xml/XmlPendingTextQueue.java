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
package srojak.xml;

import java.util.Objects;
import java.util.function.Function;

import srojak.core.CommonCollectionSize;
import srojak.core.collections.TQueue;

/**
 * @author Stephen
 *
 */
public class XmlPendingTextQueue
		implements CommonCollectionSize {
	private final TQueue<String> _queue;
	
	public XmlPendingTextQueue() {
		_queue = new TQueue<String>();
	}

	@Override
	public boolean isEmpty() {
		return _queue.isEmpty();
	}

	@Override
	public int size() {
		return _queue.size();
	}
	
	public void clear() {
		_queue.clear();
	}
	
	public void enqueue(String str) {
		Objects.requireNonNull(str, "str");
		_queue.enqueue(str);
	}
	
	public void enqueueTransformed(String str, Function<String, String> fnTransform) {
		Objects.requireNonNull(str, "str");
		Objects.requireNonNull(fnTransform, "fnTransform");
		str = fnTransform.apply(str);
		if (str != null) {
			_queue.enqueue(str);		
		}
	}
	
	public void enqueue(StringBuilder sb) {
		Objects.requireNonNull(sb, "sb");
		_queue.enqueue(sb.toString());
	}
	
	public String dequeue() {
		return _queue.dequeue();
	}
}
