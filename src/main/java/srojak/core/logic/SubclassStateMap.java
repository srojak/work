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
package srojak.core.logic;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import srojak.core.CommonCollectionSize;
import srojak.core.DuplicateKeyException;
import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class SubclassStateMap 
		implements CommonCollectionSize {
	private final HashMap<ClassKey, ClassInitFlags> _map;
	
	public SubclassStateMap() {
		_map = new HashMap<ClassKey, ClassInitFlags>();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}

	public ClassInitFlags create(ClassKey key) {
		Objects.requireNonNull(key, "key");
		if (_map.containsKey(key)) {
			throw new DuplicateKeyException(key.toString());
		}
		ClassInitFlags flags = new ClassInitFlags();
		_map.put(key, flags);
		return flags;
	}
	
	public ClassInitFlags getFlags(ClassKey key) {
		Objects.requireNonNull(key, "key");
		ClassInitFlags flags = _map.get(key);
		if (flags == null) {
			throw new NoSuchElementException(key.toString());
		}
		return flags;
	}
}
