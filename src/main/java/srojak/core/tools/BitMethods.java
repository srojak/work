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
package srojak.core.tools;

/**
 * @author Stephen
 *
 */
public class BitMethods {

	public static int sett(int flags, int ... masks) {
		for (int mask : masks) {
			flags |= mask;
		}
		return flags;
	}
	
	public static long set(long flags, long ... masks) {
		for (long mask : masks) {
			flags |= mask;
		}
		return flags;
	}
	
	public static int clear(int flags, int ... masks) {
		for (int mask : masks) {
			flags &= ~mask;
		}
		return flags;
	}
	
	public static long clear(long flags, long ... masks) {
		for (long mask : masks) {
			flags &= ~mask;
		}
		return flags;
	}
	
	public static boolean test(int flags, int mask) {
		return (flags & mask) != 0;
	}
	
	public static boolean test(int flags, int maskFirst, int ... masks) {
		for (int mask : masks) {
			maskFirst |= mask;
		}
		return (flags & maskFirst) != 0;
	}
	
	public static boolean test(long flags, long mask) {
		return (flags & mask) != 0;
	}
	
	public static boolean test(long flags, long maskFirst, long ... masks) {
		for (long mask : masks) {
			maskFirst |= mask;
		}
		return (flags & maskFirst) != 0;
	}
}
