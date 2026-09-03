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
package srojak.cdo.swing;

/**
 * @author Stephen
 *
 */
public interface ComponentFacadeFlags {

	static final int CFF_SYNTH_ENABLED = 0x1;
	static final int CFF_MODEL_ENABLED = 0x2;
	static final int CFF_PARENT_ENABLED = 0x4;
	static final int CFF_LOCAL_ENABLED = 0x8;
}
