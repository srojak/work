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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import srojak.core.KeyValue;
import srojak.core.Tuple;
import srojak.core.containers.TupleContainer;
import srojak.core.tools.KeyValueMethods;
import srojak.numerics.CircleOctant;
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
	public S2Direction findNearestDirection(S2Offset offset) {
		if (offset.dx == 0 && offset.dy == 0) {
			return S2SymbolicDirection.None;
		}
		double dTheta = Math.atan2((double) offset.dy, (double) offset.dx);
		CircleOctant octant = CircleOctant.getOctantFor(dTheta);
		return _mapOctants.get(octant);
	}

	@Override
	public S2Direction findDirection(S2Offset offset) {
		if (offset.dx == 0 && offset.dy == 0) {
			return S2SymbolicDirection.None;
		}
		Tuple<Integer> tkey = new TupleContainer<Integer>(Integer.valueOf(Integer.signum(offset.dx)),
				Integer.valueOf(Integer.signum(offset.dy)));
		KeyValue<Tuple<Integer>, S2CompassDirection> loc
			= KeyValueMethods.findFirstIn(tkey, _listLocators);
		return loc.getValue();
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

}
