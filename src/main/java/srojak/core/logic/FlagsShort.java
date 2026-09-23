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
public final class FlagsShort 
		implements FlagsShortTest {
	private short _flags;
	
	public FlagsShort() {
		_flags = 0;
	}
	
	public void set(short ... masks) {
		for (int m : masks) {
			_flags |= m;
		}
	}
	
	public void clear(short ... masks) {
		for (int m : masks) {
			_flags &= ~m;
		}
	}
	
	public boolean apply(boolean bState, short ... masks) {
		int flagsOrig = _flags;
		if (bState) {
			set(masks);
		} else {
			clear(masks);
		}
		return (flagsOrig != _flags);
	}

	@Override
	public boolean test(short mask) {
		return (_flags & mask) != 0;
	}

	@Override
	public boolean testAnd(short maskFirst, short... masks) {
		for (short m : masks) {
			maskFirst |= m;
		}
		return (_flags & maskFirst) == maskFirst;
	}

	@Override
	public boolean testOr(short maskFirst, short... masks) {
		for (short m : masks) {
			maskFirst |= m;
		}
		return (_flags & maskFirst) != 0;
	}

	@Override
	public String toString() {
		return "0x" + Integer.toHexString(_flags);
	}

}
