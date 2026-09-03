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
module srojak.cdo.swing {
	requires transitive java.desktop;
	requires transitive srojak.core;
	requires transitive srojak.mantle;
	requires transitive srojak.numerics;
	requires transitive srojak.valuestore;
	requires transitive srojak.events;
	requires transitive srojak.cdo;
	requires srojak.debug;
	requires srojak.debug.cdo;
	exports srojak.cdo.swing;
	exports srojak.cdo.swing.base;
	exports srojak.cdo.swing.collections;
	exports srojak.cdo.swing.components;
	exports srojak.cdo.swing.event;
	exports srojak.cdo.swing.frames;
	exports srojak.cdo.swing.functional;
	exports srojak.cdo.swing.interact;
	exports srojak.cdo.swing.lists;
	exports srojak.cdo.swing.models;
	exports srojak.cdo.swing.observe;
	exports srojak.cdo.swing.panels;
	exports srojak.cdo.swing.status;
	exports srojak.cdo.swing.trees;
	exports srojak.cdo.swing.workers;
}