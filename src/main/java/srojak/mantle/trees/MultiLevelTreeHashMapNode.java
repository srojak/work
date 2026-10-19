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

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.keys.MultiPartKeyPart;

/**
 * @author Stephen
 *
 */
public class MultiLevelTreeHashMapNode<E> 
		extends MultiLevelTreeNodeBase<E>
		implements MultiLevelTreeNode<E> {
	private final HashMap<MultiPartKeyPart, MultiLevelTreeNode<E>> _mapChildren;
	
	public MultiLevelTreeHashMapNode() {
		super();
		_mapChildren = new HashMap<MultiPartKeyPart, MultiLevelTreeNode<E>>();
	}

	@Override
	public boolean containsKey(MultiPartKeyPart keyPart) {
		return _mapChildren.containsKey(keyPart);
	}

	@Override
	public boolean hasNodes() {
		return _mapChildren.isEmpty();
	}

	@Override
	public int nodes() {
		return _mapChildren.size();
	}

	@Override
	public MultiLevelTreeNode<E> getNode(MultiPartKeyPart keyPart) {
		return _mapChildren.get(keyPart);
	}

	@Override
	public MultiLevelTreeNode<E> getOrAddNode(MultiPartKeyPart keyPart) {
		Objects.requireNonNull(keyPart, "keyPart");
		MultiLevelTreeNode<E> node = _mapChildren.get(keyPart);
		if (node == null) {
			node = new MultiLevelTreeHashMapNode<E>();
			_mapChildren.put(keyPart, node);
		}
		return node;
	}

	@Override
	public Stream<MultiPartKeyPart> allNodeKeys() {
		return _mapChildren.keySet().stream();
	}

}
