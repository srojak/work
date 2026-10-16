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
package srojak.kernel.modules.impl;

import java.io.Serializable;

import srojak.core.keys.EquatableSequentialKeyBase;
import srojak.core.modules.ModuleName;
import srojak.kernel.modules.ModuleLayerKey;

/**
 * @author Stephen
 *
 */
public class ModuleLayerKeyObj 
		extends EquatableSequentialKeyBase 
		implements ModuleLayerKey {

	/**
	 * 
	 */
	public static final long serialClassUID = -706762484099852382L;
	private static final String nameRoot = ModuleLayerKey.class.getSimpleName();

	/**
	 * @param lnValue
	 */
	public ModuleLayerKeyObj(long lnValue) {
		super(lnValue);
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
	public boolean isEqual(ModuleLayerKey obj) {
		return commonEquals(obj);
	}

}
