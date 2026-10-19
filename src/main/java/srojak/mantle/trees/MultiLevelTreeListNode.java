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
package srojak.mantle.trees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import srojak.core.containers.KeyValueContainer;
import srojak.core.keys.MultiPartKeyPart;
import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
public class MultiLevelTreeListNode<E>
		extends MultiLevelTreeNodeBase<E> {
	private final LinkedList<ListEntry<E>> _listChildren;
	
	public MultiLevelTreeListNode() {
		super();
		_listChildren = new LinkedList<ListEntry<E>>();
	}
	
	private MultiLevelTreeNode<E> findNodeByKey(MultiPartKeyPart keyPart) {
		Iterator<ListEntry<E>> iterator = _listChildren.iterator();
		while (iterator.hasNext()) {
			ListEntry<E> entry = iterator.next();
			if (entry.isKeyEqual(keyPart)) {
				return entry.value();
			}
		}
		return null;
	}

	@Override
	public boolean hasNodes() {
		return !_listChildren.isEmpty();
	}

	@Override
	public int nodes() {
		return _listChildren.size();
	}

	@Override
	public MultiLevelTreeNode<E> getNode(MultiPartKeyPart keyPart) {
		return findNodeByKey(keyPart);
	}

	@Override
	public MultiLevelTreeNode<E> getOrAddNode(MultiPartKeyPart keyPart) {
		ListEntry<E> container = ListMethods.findInList(_listChildren, e -> e.isKeyEqual(keyPart));
		if (container == null) {
			container = new ListEntry<E>(keyPart, new MultiLevelTreeListNode<E>());
			_listChildren.add(container);
		}
		return container.value();
	}

	@Override
	public Stream<MultiPartKeyPart> allNodeKeys() {
		return  _listChildren.stream().map(e -> e.key());
	}

	@Override
	public boolean containsKey(MultiPartKeyPart keyPart) {
		return ListMethods.isTrueForAny(_listChildren, e -> e.isKeyEqual(keyPart));
	}
	
	private class ListEntry<E> 
		extends KeyValueContainer<MultiPartKeyPart, MultiLevelTreeNode<E>> {

		/**
		 * @param key
		 * @param value
		 */
		public ListEntry(MultiPartKeyPart key, MultiLevelTreeNode<E> value) {
			super(key, value);
		}
					
	}
}
