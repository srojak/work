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
package srojak.numerics;

/**
 * @author Stephen
 *
 */
public enum IntervalType {
	OPEN(false, false),
	OPEN_RIGHT(true, false),
	OPEN_LEFT(false, true),
	CLOSED(true, true);
	
	private final boolean _bLeftClosed;
	private final boolean _bRightClosed;
	public final char charLeftSide;
	public final char charRightSide;
	
	private IntervalType(boolean bLeft,boolean bRight) {
		_bLeftClosed = bLeft;
		_bRightClosed = bRight;
		charLeftSide = _bLeftClosed ? '[' : '(';
		charRightSide = _bRightClosed ? ']' : ')';
	}
	
	public boolean isLeftClosed() {
		return _bLeftClosed;
	}
	
	public boolean isRightClosed() {
		return _bRightClosed;
	}
	
	public OrderedComparison getLeftComparison() {
		return _bLeftClosed ? OrderedComparison.GE : OrderedComparison.GT;
	}
	
	public OrderedComparison getRightComparison() {
		return _bRightClosed ? OrderedComparison.LE : OrderedComparison.LT;
	}
	
	public boolean evalLeftComparison(int nCompar) {
		return _bLeftClosed ? nCompar <= 0 : nCompar < 0;
	}
	
	public boolean evalRightComparison(int nCompar) {
		return _bRightClosed ? nCompar >= 0 : nCompar > 0;
	}
}
