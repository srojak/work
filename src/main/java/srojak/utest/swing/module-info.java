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
 */
module srojak.utest.swing {
	requires transitive java.desktop;
	requires transitive srojak.core;
	requires transitive srojak.mantle;
	requires transitive srojak.numerics;
	requires transitive srojak.cdo;
	requires transitive srojak.cdo.swing;
	requires transitive srojak.cdo.uilib;
	requires transitive srojak.xml;
	requires transitive srojak.debug;
	requires srojak.debug.cdo;
	requires transitive srojak.debug.config;
	requires srojak.utest;
	requires srojak.gui;
	exports srojak.utest.swing;
}