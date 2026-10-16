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
package srojak.kernel;

import srojak.core.keys.NameEquatableKeyBase;

/**
 * @author Stephen
 *
 */
public class KernelSequenceKeyObj 
		extends NameEquatableKeyBase 
		implements KernelSequenceKey {

	/**
	 * 
	 */
	private static final long serialClassUID = 2474243339132851499L;
	private static final String nameRoot = KernelSequenceKey.class.getSimpleName();

	/**
	 * @param strName
	 */
	public KernelSequenceKeyObj(String strName) {
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
	public int compareTo(KernelSequenceKey o) {
		return super.commonComparer(o);
	}

}
