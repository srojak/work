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

/**
 * @author Stephen
 *
 */
public class FlagsInt 
		implements FlagsIntTest {
	private int _flags;
	
	public FlagsInt() {
		_flags = 0;
	}
	
	public void set(int ... masks) {
		for (int m : masks) {
			_flags |= m;
		}
	}
	
	public void clear(int ... masks) {
		for (int m : masks) {
			_flags &= ~m;
		}
	}

	@Override
	public boolean test(int mask) {
		return (_flags & mask) != 0;
	}

	@Override
	public boolean testAnd(int maskFirst, int... masks) {
		for (int m : masks) {
			maskFirst |= m;
		}
		return (_flags & maskFirst) == maskFirst;
	}

	@Override
	public boolean testOr(int maskFirst, int... masks) {
		for (int m : masks) {
			maskFirst |= m;
		}
		return (_flags & maskFirst) != 0;
	}

	@Override
	public String toString() {
		return "0x" + Integer.toHexString(_flags);
	}

}
