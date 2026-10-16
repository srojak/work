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
package srojak.core.observe.code;

import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public enum CodeValueUse
		implements CodeValueUseFlags {
	Unknown(F_NONE),
	MethodLocal(F_HAS_NAME|F_METHOD_LOCAL|F_CLASS_LOCAL),
	MethodParameter(F_HAS_NAME|F_METHOD_LOCAL|F_CLASS_LOCAL),
	MethodReturn(F_METHOD_LOCAL|F_CLASS_LOCAL),
	ClassInstance(F_HAS_NAME|F_CLASS_LOCAL),
	ClassStatic(F_HAS_NAME|F_CLASS_LOCAL),
	External(F_HAS_NAME);
	
	private final int _flags;
	
	private CodeValueUse(int flags) {
		_flags = flags;
	}
	
	public boolean hasName() {
		return BitMethods.test(_flags, F_HAS_NAME);
	}
	
	public boolean isMethodLocal() {
		return BitMethods.test(_flags, F_METHOD_LOCAL);
	}
	
	public boolean isClassLocal() {
		return BitMethods.test(_flags, F_CLASS_LOCAL);
	}
}
