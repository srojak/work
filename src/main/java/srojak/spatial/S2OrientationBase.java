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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import srojak.core.KeyValue;
import srojak.core.Tuple;
import srojak.core.containers.TupleContainer;
import srojak.core.tools.KeyValueMethods;
import srojak.numerics.CircleOctant;
import srojak.numerics.compass.CompassDegrees;
import srojak.spatial.impl.S2DirectionMapping;

/**
 * @author Stephen
 *
 */
public abstract class S2OrientationBase
		implements S2Orientation {
	private final List<KeyValue<S2CompassDirection, S2Offset>> _listOffsets;
	private final List<KeyValue<Tuple<Integer>, S2CompassDirection>> _listLocators;
	private final Map<CircleOctant, S2CompassDirection> _mapOctants;
	
	S2OrientationBase(Collection<S2DirectionMapping> collMappings) {
		_listOffsets = new ArrayList<KeyValue<S2CompassDirection, S2Offset>>(8);	
		_listLocators = new ArrayList<KeyValue<Tuple<Integer>, S2CompassDirection>>(8);
		_mapOctants = new HashMap<CircleOctant, S2CompassDirection>();
		for (S2DirectionMapping mapping : collMappings) {
			Tuple<Integer> locator = mapping.getLocatorKey();
			_listOffsets.add(KeyValueMethods.pair(mapping.getDirection(),
					new S2Offset(locator.getFirstValue().intValue(), 
							locator.getSecondValue().intValue())));
			_listLocators.add(KeyValueMethods.pair(locator, mapping.getDirection()));
			_mapOctants.put(mapping.getOctant(), mapping.getDirection());
		}
	}
	
	public List<KeyValue<S2CompassDirection, S2Offset>> getOffsetsList() {
		return List.copyOf(_listOffsets);
	}

	@Override
	public S2CompassDirection getDirectionFromOctant(CircleOctant octant) {
		Objects.requireNonNull(octant, "octant");
		return _mapOctants.get(octant);
	}

	@Override
	public S2Direction findNearestDirection(S2Offset offset) {
		if (offset.dx == 0 && offset.dy == 0) {
			return S2SymbolicDirection.None;
		}
		double dTheta = Math.atan2((double) offset.dy, (double) offset.dx);
		CircleOctant octant = CircleOctant.getOctantFor(dTheta);
		return _mapOctants.get(octant);
	}
	
	protected S2CompassDirection findCompassDirectionInner(S2Offset offset) {
		Tuple<Integer> tkey = new TupleContainer<Integer>(Integer.valueOf(Integer.signum(offset.dx)),
				Integer.valueOf(Integer.signum(offset.dy)));
		KeyValue<Tuple<Integer>, S2CompassDirection> loc
			= KeyValueMethods.findFirstIn(tkey, _listLocators);
		return loc.getValue();
	}

	@Override
	public S2Direction findDirection(S2Offset offset) {
		Objects.requireNonNull(offset, "offset");
		if (offset.isZero()) {
			return S2SymbolicDirection.None;
		}
		return findCompassDirectionInner(offset);
	}

	@Override
	public S2CompassDirection findCompassDirection(S2Offset offset)
			throws NoValidMoveException {
		Objects.requireNonNull(offset, "offset");
		if (offset.isZero()) {
			throw new NoValidMoveException("offset is zero");
		}
		return findCompassDirectionInner(offset);
	}

	@Override
	public S2Offset offsetByOne(S2CompassDirection direction) {
		Objects.requireNonNull(direction, "direction");
		KeyValue<S2CompassDirection, S2Offset> pair = KeyValueMethods.findFirstIn(direction, _listOffsets);
		return pair.getValue();
	}

	@Override
	public S2Offset offset(S2CompassDirection direction, int nDistance) {
		Objects.requireNonNull(direction, "direction");
		if (nDistance < 0) {
			throw new IllegalArgumentException("nDistance is negative");
		} else if (nDistance == 0) {
			return new S2Offset(0, 0);
		}
		S2Offset offsetBase = offsetByOne(direction);
		return new S2Offset(offsetBase.getX() * nDistance, offsetBase.getY() * nDistance);
	}
	
	protected abstract S2Offset makeOffsetFrom(int dx, int dy);
	
	@Override
	public S2Offset offset(double dRadians, float fDistance) {
		int dx = (int) Math.round(fDistance + Math.cos(dRadians));
		int dy = (int) Math.round(fDistance + Math.sin(dRadians));
		return makeOffsetFrom(dx, dy);
	}

	protected abstract S2Rect findSideRect(S2CompassDirection direction, S2FieldSize szField, 
			int nWidth, int nHeight);

	@Override
	public S2Rect getSideRect(S2CompassDirection direction, S2FieldSize szField, int nWidth, int nHeight) {
		Objects.requireNonNull(direction, "direction");
		Objects.requireNonNull(szField, "szField");
		if (nWidth <= 0) {
			throw new IllegalArgumentException("nWidth must be positive");
		}
		if (nHeight <= 0) {
			throw new IllegalArgumentException("nHeight must be positive");
		}
		if (nWidth > szField.width) {
			nWidth = szField.width;
		}
		if (nHeight > szField.height) {
			nHeight = szField.height;
		}
		return findSideRect(direction, szField, nWidth, nHeight);
	}
	
	@Override
	public CompassDegrees findDegreesFor(S2Offset offset) {
		Objects.requireNonNull(offset, "offset");
		// try to avoid complex calculations for simple moves
		S2CompassDirection dirVert = getIncreasingVerticalDirection();
		if (offset.dx == 0) {
			if (offset.dy >= 0) {
				return dirVert.getDegrees();
			} else {
				return dirVert.getOppositeDirection().getDegrees();
			}
		} else if (offset.dy == 0) {
			S2CompassDirection dir = getIncreasingHorizontalDirection();
			if (offset.dx > 0) {
				return dir.getDegrees();
			} else {
				return dir.getOppositeDirection().getDegrees();
			}
		} else {
			double dRadians = Math.atan2(offset.dy, offset.dx);
			return CompassDegrees.convertFromRadians(dRadians);
		}
	}
	
	@Override
	public S2UnitRay findUnitVector(S2Coords coordsFrom, S2Coords coordsTo) 
			throws NoValidMoveException {
		Objects.requireNonNull(coordsFrom, "coordsFrom");
		Objects.requireNonNull(coordsTo, "coordsTo");
		S2Offset offsetMove = coordsFrom.getOffsetTo(coordsTo);
		if (!offsetMove.isAdjacent() || offsetMove.isZero()) {
			throw new NoValidMoveException("coords are not adjacent");
		}
		S2Direction direction = findDirection(offsetMove);
		return new S2UnitRay(coordsFrom, direction.getAsCompassDirection());
	}

	private S2RayFixedHeading computeVector(S2Coords coordsFrom, S2Coords coordsTo) {
		S2Offset offsetMove = coordsFrom.getOffsetTo(coordsTo);
		CompassDegrees bearing = findDegreesFor(offsetMove);
		return new S2RayFixedHeading(coordsFrom, bearing, (float) offsetMove.getDistance());
	}
	
	@Override
	public S2RayFixedHeading findVector(S2Coords coordsFrom, S2Coords coordsTo) {
		Objects.requireNonNull(coordsFrom, "coordsFrom");
		Objects.requireNonNull(coordsTo, "coordsTo");
		return computeVector(coordsFrom, coordsTo);
	}

	@Override
	public List<S2RayFixedHeading> getVectorsFrom(Collection<S2Coords> coords) {
		Objects.requireNonNull(coords, "coords");
		int nCoords = coords.size();
		ArrayList<S2RayFixedHeading> list = new ArrayList<S2RayFixedHeading>(nCoords > 1 ? nCoords - 1 : 0);
		S2Coords coordsFrom = null;
		Iterator<S2Coords> iterator = coords.iterator();
		if (iterator.hasNext()) {
			coordsFrom = iterator.next();
		}
		while (iterator.hasNext()) {
			S2Coords coordsTo = iterator.next();
			S2RayFixedHeading vector = computeVector(coordsFrom, coordsTo);
			list.add(vector);
			coordsFrom = coordsTo;
		}
		return list;
	}
}
