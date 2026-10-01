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

/**
 * @author Stephen
 *
 */
public interface XResultModifierFlags {

	public static int MOD_DONE_ONCE = 0x1;
	public static int MOD_NO_FILE_TO_READ = 0x2;
	public static int MOD_EXCEPT_ON_CLOSE = 0x4;
	public static int MOD_NO_ACTION_REQD = 0x8;
	public static int MOD_FROM_SURROUND = 0x10;
}
