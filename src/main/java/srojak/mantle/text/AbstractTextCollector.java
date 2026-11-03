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
package srojak.mantle.text;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class AbstractTextCollector 
		implements TextCollector {
	private final List<TextReceiver> _listReceivers;
	
	protected AbstractTextCollector() {
		_listReceivers = Collections.synchronizedList(new LinkedList<TextReceiver>());
	}
	
	protected void writeToAll(String strText, boolean bImpliedNewline) {
		List<TextReceiver> listSnap = _listReceivers.stream().toList();
		Iterator<TextReceiver> iterator = listSnap.iterator();
		while (iterator.hasNext()) {
			TextReceiver receiver = iterator.next();
			if (receiver.isOpen()) {
				receiver.receive(strText, bImpliedNewline);
			} else {
				_listReceivers.remove(receiver);
			}
		}
	}

	@Override
	public void addReceiver(TextReceiver receiver) {
		Objects.requireNonNull(receiver, "receiver");
		_listReceivers.add(receiver);
	}

	@Override
	public void removeReceiver(TextReceiver receiver) {
		Objects.requireNonNull(receiver, "receiver");
		_listReceivers.remove(receiver);
	}

	@Override
	public void unloadAllReceivers() {
		_listReceivers.clear();
	}
}
