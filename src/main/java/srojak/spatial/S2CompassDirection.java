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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.core.tools.CollectionMethods;
import srojak.numerics.CircleOctant;
import srojak.numerics.IntervalType;
import srojak.numerics.compass.CompassDegrees;
import srojak.numerics.compass.CompassOrdinals;
import srojak.numerics.compass.CompassPoint;
import srojak.numerics.intervals.IntervalFloat;
import srojak.numerics.weighted.DoubleWeighted;
import srojak.numerics.weighted.DoubleWeightedArrayList;
import srojak.numerics.weighted.DoubleWeightedList;
import srojak.numerics.weighted.DoubleWeightedObject;

/**
 * @author Stephen
 *
 */
public final class S2CompassDirection
		extends S2Direction
		implements CompassOrdinals {
	private final CompassPoint _point;
	
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
	private static final Map<CompassPoint, S2CompassDirection> _mapPoints;
	private static final IntervalFloat _intvDegrees;
	private static final float _fAdjustNorth;
	
	static {
		LinkedList<S2CompassDirection> dirs = new LinkedList<S2CompassDirection>();
		S2CompassDirection rd = new S2CompassDirection("N", "North", CompassPoint.N);
		North = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("NE", "N-East", CompassPoint.NE);
		NorthEast = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("E", "East", CompassPoint.E);
		East = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("SE", "S-East", CompassPoint.SE);
		SouthEast = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("S", "South", CompassPoint.S);
		South = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("SW", "S-West", CompassPoint.SW);
		SouthWest = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("W", "West", CompassPoint.W);
		West = rd;
		dirs.add(rd);
		rd = new S2CompassDirection("NW", "N-West",CompassPoint.NW);
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
		_mapPoints = new HashMap<CompassPoint, S2CompassDirection>();
		for (S2CompassDirection direction : dirs) {
			_mapPoints.put(direction.getCompassPoint(), direction);
		}
		_intvDegrees = new IntervalFloat(IntervalType.OPEN_RIGHT, -22.5f, 22.5f);
		_fAdjustNorth = CompassDegrees.LIMIT + _intvDegrees.getMinimum();
	}

	protected S2CompassDirection(String strAbbrev, String strName, CompassPoint point) {
		super(strAbbrev, point.getOrdinal(), strName);
		_point = point;
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
		return _point.getDegrees();
	}
	
	public CompassPoint getCompassPoint() {
		return _point;
	}
	
	public CircleOctant getOctant() {
		return _point.getOctant();
	}
	
	public boolean isCardinalDirection() {
		return _point.isCardinal();
	}
	
	public S2CompassDirection getOppositeDirection() {
		return _mapOpposites.get(this);
	}
	
	public List<S2CompassDirection> findCardinalDirections() {
		List<S2CompassDirection> list = null;
		switch (_point) {
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
	
	public static S2CompassDirection getDirectionFor(CompassPoint cpoint) {
		Objects.requireNonNull(cpoint, "cpoint");
		return _mapPoints.get(cpoint);
	}
	
	public static S2CompassDirection findDirectionFor(CompassDegrees cdg) {
		Objects.requireNonNull(cdg, "cdg");
		float fInput = cdg.getValue();
		if (fInput > _fAdjustNorth) {
			fInput -= CompassDegrees.LIMIT;
		}
		for (S2CompassDirection direction : AllDirs) {
			float delta = fInput - direction.getDegrees().getValue();
			if (_intvDegrees.isInInterval(delta)) {
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
	
	public static S2CompassDirection findDirectionWhere(Predicate<S2CompassDirection> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return CollectionMethods.findFirstIn(AllDirs, predicate);
	}
	
	public static S2CompassDirection findDirectionByAbbrev(String strAbbrev) {
		return findDirectionWhere(d -> d.getAbbrev().equals(strAbbrev));
	}
	
	public static DoubleWeightedList constructWeigthedList(double dWeightCardinal, double dWeightOther) {
		DoubleWeightedArrayList list = new DoubleWeightedArrayList(8);
		for (S2CompassDirection direction : AllDirs) {
			DoubleWeighted objWeigthed = new DoubleWeightedObject(direction,
					direction.isCardinalDirection() ? dWeightCardinal : dWeightOther);
			list.add(objWeigthed);
		}
		list.assignWeights();
		return list;
	}
}
