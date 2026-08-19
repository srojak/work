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
package srojak.core.events;

/**
 * @author Stephen
 *
 */
public interface StateChangeCodes {
	
	/**
	 * The state is the overall state of the object.
	 */
	public static final int ID_SELF = 10;
	
	/**
	 * The state is the state of an operation.
	 */
	public static final int ID_OPERATION = 11;
	
	/**
	 * The state is the state of an element in a defined set.
	 */
	public static final int ID_CHOICE = 12;
}
