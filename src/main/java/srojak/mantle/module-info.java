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
/**
 * @author Stephen
 *
 * Objects at this level can use the debugging facility.
 * 
 * The dependency on srojak.numerics is to prevent the latter from depending on this module.
 */
module srojak.mantle {
	requires transitive srojak.core;
	requires transitive srojak.numerics;
	requires transitive srojak.debug;
	exports srojak.mantle;
	exports srojak.mantle.collections;
	exports srojak.mantle.functional;
	exports srojak.mantle.io;
	exports srojak.mantle.reflect;
}