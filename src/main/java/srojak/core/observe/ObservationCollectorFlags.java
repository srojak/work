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
package srojak.core.observe;

import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public interface ObservationCollectorFlags {
	
	static final short FLAGS_HAS_PERM_WRITER = 0x1;
	static final short FLAGS_WRITERS_LOCKED = 0x2;
	static final short FLAGS_USE_FULL_CLASS_NAMES = 0x4;
	static final short FLAGS_SUPPRESS_ALERTS = 0x8;
	static final short FLAGS_OBS_LEVEL_LOCKED = 0x10;
	static final short FLAGS_SUPPRESS_SOURCE = 0x20;

	FlagsShortTest getFlags();
	boolean hasPermanentWriters();
	boolean areWritersLocked();
	boolean useFullClassNames();
	boolean isObsLevelLocked();
	boolean showSourceLocations();
}
