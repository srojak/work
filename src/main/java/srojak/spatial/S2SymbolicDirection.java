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
package srojak.spatial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephen
 *
 * Symbolic pseudo-directions.
 */
public final class S2SymbolicDirection
		extends S2Direction {
	
	public static final int ORDINAL_NONE = 1;
	public static final int ORDINAL_RANDOM = 2;
	
	public static final S2SymbolicDirection None;
	public static final S2SymbolicDirection Random;
	public static final List<S2SymbolicDirection> AllDirs;
	
	static {
		ArrayList<S2SymbolicDirection> dirs = new ArrayList<S2SymbolicDirection>();
		None = new S2SymbolicDirection("@", ORDINAL_NONE, "None");
		Random = new S2SymbolicDirection("?", ORDINAL_RANDOM, "Random");
		dirs.forEach(d -> register(d));
		AllDirs = Collections.unmodifiableList(dirs);
	}
	
	/**
	 * @param strAbbrev
	 * @param ordinal
	 * @param strName
	 */
	protected S2SymbolicDirection(String strAbbrev, int ordinal, String strName) {
		super(strAbbrev, ordinal, strName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getDirType() {
		return TYPE_SYMBOLIC;
	}

}
