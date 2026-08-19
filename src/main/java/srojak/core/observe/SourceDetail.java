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
package srojak.core.observe;

/**
 * @author Stephen
 *
 */
public enum SourceDetail
		implements SourceDetailFlags {
	NONE(0),
	METHOD_ONLY(FLAG_METHOD),
	CLASS_ONLY(FLAG_CLASS),
	CLASS_METHOD(FLAG_CLASS|FLAG_METHOD),
	CLASS_METHOD_LINE(FLAG_CLASS|FLAG_METHOD|FLAG_LINE),
	PACKAGE_CLASS_METHOD(FLAG_PACKAGE|FLAG_CLASS|FLAG_METHOD),
	ALL(FLAG_PACKAGE|FLAG_CLASS|FLAG_METHOD|FLAG_LINE);
	
	private final int _flags;
	
	private SourceDetail(int flags) {
		_flags = flags;
	}
	
	public boolean isFlagSet(int mask) {
		return (_flags & mask) != 0;
	}
}
