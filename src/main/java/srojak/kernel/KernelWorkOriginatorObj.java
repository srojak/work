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
public class KernelWorkOriginatorObj
		extends NameEquatableKeyBase
		implements KernelWorkOriginator {
	
	/**
	 * 
	 */
	private static final long serialClassUID = 1768828685334398855L;
	private static final String nameRoot = KernelWorkOriginator.class.getSimpleName();

	/**
	 * @param strName
	 */
	public KernelWorkOriginatorObj(String strName) {
		super(strName);
	}

	@Override
	public int compareTo(KernelWorkOriginator o) {
		return super.commonComparer(o);
	}

	@Override
	protected long getRootId() {
		return serialClassUID;
	}

	@Override
	protected String getRootName() {
		return nameRoot;
	}

}
