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
package srojak.core.result;

import srojak.core.StringComparable;
import srojak.core.keys.NameEquatableKeyBase;

/**
 * @author Stephen
 *
 */
public class XResultKey 
		extends NameEquatableKeyBase 
		implements StringComparable, Comparable<XResultKey> {

	/**
	 * 
	 */
	private static final long serialClassUID = -1281220075719240201L;
	private static final String nameRoot = XResultKey.class.getSimpleName();

	/**
	 * @param strName
	 */
	public XResultKey(String strName) {
		super(strName);
	}

	@Override
	protected long getRootId() {
		return serialClassUID;
	}

	@Override
	protected String getRootName() {
		return nameRoot;
	}

	@Override
	public int compareTo(XResultKey o) {
		if (o == null) {
			return 1;
		} else {
			return _name.compareTo(o.getName());
		}
	}

}
