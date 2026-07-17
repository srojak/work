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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import srojak.core.tools.CollectionMethods;
import srojak.numerics.CircleOctant;
import srojak.numerics.CompassDegrees;

/**
 * @author Stephen
 *
 */
public final class S2CompassDirection
		extends S2Direction
		implements S2CompassOrdinals {
	private final CompassDegrees _degrees;
	private final Code _code;
	private final CircleOctant _graphicsOctant;
	
	public static final S2CompassDirection North;
	public static final S2CompassDirection NorthEast;
	public static final S2CompassDirection East;
	public static final S2CompassDirection SouthEast;
	public static final S2CompassDirection South;
	public static final S2CompassDirection SouthWest;
	public static final S2CompassDirection West;
	public static final S2CompassDirection NorthWest;
	public static final List<S2CompassDirection> AllDirs;
	public static final List<S2CompassDirection> CardinalDirs;
	private static final Map<S2CompassDirection, S2CompassDirection> _mapOpposites;
	
	static {
		LinkedList<S2CompassDirection> dirs = new LinkedList<S2CompassDirection>();
		S2CompassDirection rd = new S2CompassDirection("N", OrdNorth, "North", CircleOctant.UP, Code.N);
		North = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("NE", OrdNorthEast, "N-East", CircleOctant.UPPER_RIGHT, Code.NE);
		NorthEast = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("E", OrdEast, "East", CircleOctant.RIGHT, Code.E);
		East = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("SE", OrdSouthEast, "S-East", CircleOctant.LOWER_RIGHT, Code.SE);
		SouthEast = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("S", OrdSouth, "South", CircleOctant.DOWN, Code.S);
		South = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("SW", OrdSouthWest, "S-West", CircleOctant.LOWER_LEFT, Code.SW);
		SouthWest = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("W", OrdWest, "West", CircleOctant.LEFT, Code.W);
		West = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("NW", OrdNorthWest, "N-West", CircleOctant.UPPER_LEFT, Code.NW);
		NorthWest = rd;
		dirs.add(rd);
		dirs.forEach(d -> register(d));
		AllDirs = List.copyOf(dirs);
		CardinalDirs = List.copyOf(CollectionMethods.where(dirs, d -> d.isCardinalDirection()));
		_mapOpposites = new HashMap<S2CompassDirection, S2CompassDirection>();
		_mapOpposites.put(North, South);
		_mapOpposites.put(NorthEast, SouthWest);
		_mapOpposites.put(East,  West);
		_mapOpposites.put(SouthEast, NorthWest);
		_mapOpposites.put(South, North);
		_mapOpposites.put(SouthWest,  NorthEast);
		_mapOpposites.put(West, East);
		_mapOpposites.put(NorthWest,  SouthEast);
	}

	protected S2CompassDirection(String strAbbrev, int ordinal, String strName,
			CircleOctant octant, Code code) {
		super(strAbbrev, ordinal, strName);
		_code = code;
		_degrees = new CompassDegrees(45 * ordinal);
		_graphicsOctant = octant;
	}

	@Override
	protected int getDirType() {
		return TYPE_COMPASS;
	}
	
	@Override
	public S2CompassDirection getAsCompassDirection() {
		return this;
	}

	@Override
	public CompassDegrees getDegrees() {
		return _degrees;
	}
	
	public Code getDirectionCode() {
		return _code;
	}
	
	public CircleOctant getGraphicsOctant() {
		return _graphicsOctant;
	}
	
	public boolean isCardinalDirection() {
		return _code.isCardinal();
	}
	
	public S2CompassDirection getOppositeDirection() {
		return _mapOpposites.get(this);
	}
	
	public List<S2CompassDirection> findCardinalDirections() {
		List<S2CompassDirection> list = null;
		switch (_code) {
		case NE:
			list = List.of(East, North);
			break;
			
		case SE:
			list = List.of(East, South);
			break;
			
		case SW:
			list = List.of(West, South);
			break;
			
		case NW:
			list = List.of(West, North);
			break;
			
		default:
			list = List.of(this);
			break;
		}
		return list;
	}
	
	public static S2CompassDirection findDirectionFor(CompassDegrees cdg) {
		Objects.requireNonNull(cdg, "cdg");
		for (S2CompassDirection direction : AllDirs) {
			float delta = cdg.getValue() - direction.getDegrees().getValue();
			if (delta >= -22.5f && delta <= 22.5f) {
				return direction;
			}
		}
		return null;
	}
	
	public static S2CompassDirection findDirectionWithin(CompassDegrees cdg, float fTolerance) {
		Objects.requireNonNull(cdg, "cdg");
		if (fTolerance <= 0) {
			throw new IllegalArgumentException("fTolerance must be positive");
		}
		for (S2CompassDirection direction : AllDirs) {
			float delta = cdg.getValue() - direction.getDegrees().getValue();
			if (Math.abs(delta) <= fTolerance) {
				return direction;
			}
		}
		return null;
	}
	
	public enum Code {
		N(true),
		NE(false),
		E(true),
		SE(false),
		S(true),
		SW(false),
		W(true),
		NW(false);
		
		private final boolean _bCardinal;
		
		private Code(boolean bIsCardinal) {
			_bCardinal = bIsCardinal;
		}
		
		public boolean isCardinal() {
			return _bCardinal;
		}
	}
}
