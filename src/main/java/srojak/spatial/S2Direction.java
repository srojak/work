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
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import srojak.core.Labeled;
import srojak.core.TextRepresentation;
import srojak.core.text.LabeledEnvelope;
import srojak.numerics.CompassDegrees;
import srojak.spatial.impl.S2DirectionRepListAbbrev;

/**
 * @author Stephen
 *
 */
public abstract sealed class S2Direction
		permits S2CompassDirection, S2SymbolicDirection {
	private final String _strName;
	private final String _strAbbrev;
	private final int _nOrdinal;
	
	public static final TextRepresentation TEXT_LIST_ABBREV;
	private static final ArrayList<S2Direction> _AllDirs;
	
	protected static final int TYPE_COMPASS = 1;
	protected static final int TYPE_SYMBOLIC = 2;
	
	static {
		TEXT_LIST_ABBREV = new S2DirectionRepListAbbrev();
		_AllDirs = new ArrayList<S2Direction>();
	}
	
	public static List<S2Direction> getAll() {
		return _AllDirs.stream().toList();
	}
	
	protected static void register(S2Direction direction) {
		Objects.requireNonNull(direction, "direction");
		_AllDirs.add(direction);
	}
	
	public static S2Direction findDirection(int dx, int dy) {
		S2Direction direction = null;
		switch (Integer.signum(dx)) {
		case -1:
			switch (Integer.signum(dy)) {
			case -1:
				direction = S2CompassDirection.NorthWest;
				break;
				
			case 0:
				direction = S2CompassDirection.West;
				break;
				
			case 1:
				direction = S2CompassDirection.SouthWest;
				break;
			}
			break;
			
		case 0:
			switch (Integer.signum(dy)) {
			case -1:
				direction = S2CompassDirection.North;
				break;
				
			case 0:
				direction = S2SymbolicDirection.None;
				break;
				
			case 1:
				direction = S2CompassDirection.South;
				break;
			}
			break;
			
		case 1:
			switch (Integer.signum(dy)) {
			case -1:
				direction = S2CompassDirection.NorthEast;
				break;
				
			case 0:
				direction = S2CompassDirection.East;
				break;
				
			case 1:
				direction = S2CompassDirection.SouthEast;
				break;
			}
			break;
			
		}
		return direction;
	}
	
	public static List<S2Direction> getCompassList(boolean bIncludeBlank) {
		ArrayList<S2Direction> list
			= new ArrayList<S2Direction>(8 + (bIncludeBlank ? 1 : 0));
		if (bIncludeBlank) {
			list.add(S2SymbolicDirection.None);
		}
		list.addAll(S2CompassDirection.AllDirs);
		return list;
	}
	
	public static List<Labeled<S2Direction>> getLabeledCompassList (
			boolean bIncludeBlank, Function<S2Direction, String> functionLabel) {
		Objects.requireNonNull(functionLabel, "functionLabel");
		ArrayList<Labeled<S2Direction>> list
			= new ArrayList<Labeled<S2Direction>>(8 + (bIncludeBlank ? 1 : 0));
		if (bIncludeBlank) {
			list.add(new LabeledEnvelope<S2Direction>(S2SymbolicDirection.None, ""));
		}
		list.addAll(S2CompassDirection.AllDirs.stream()
				.map(d -> new LabeledEnvelope<S2Direction>(d, functionLabel.apply(d))).toList());
		return list;
	}
	
	protected S2Direction(String strAbbrev, int ordinal, String strName) {
		Objects.requireNonNull(strAbbrev, "strAbbrev");
		Objects.requireNonNull(strName, "strName");
		_strName = strName;
		_strAbbrev = strAbbrev;
		_nOrdinal = ordinal;
	}
	
	public final int getOrdinal() {
		return _nOrdinal;
	}	
	
	public final String getName() {
		return _strName;
	}
	
	public final String getAbbrev() {
		return _strAbbrev;
	}
	
	public String getListAbbrev() {
		return _strAbbrev;
	}
	
	protected abstract int getDirType();
	
	public final boolean isCompass() {
		return getDirType() == TYPE_COMPASS;
	}
	
	public final boolean isSymbolic() {
		return getDirType() == TYPE_SYMBOLIC;
	}
	
	public CompassDegrees getDegrees() {
		return null;
	}
	
	public S2CompassDirection getAsCompassDirection() {
		throw new ClassCastException("not a compass direction");
	}

	@Override
	public final int hashCode() {
		return Objects.hash(getDirType(), _nOrdinal);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2Direction other) {
			return getDirType() == other.getDirType()
					&& _nOrdinal == other._nOrdinal;
		} else
			return false;
	}

	@Override
	public final String toString() {
		return _strName;
	}
}
