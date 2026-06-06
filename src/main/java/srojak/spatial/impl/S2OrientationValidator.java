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
package srojak.spatial.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import srojak.core.KeyValue;
import srojak.core.containers.KeyValueContainer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterContainerBase;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.core.specialized.IntegerCounter;
import srojak.core.tools.KeyValueMethods;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2OrientationBase;

/**
 * @author Stephen
 *
 */
public class S2OrientationValidator
		extends ObservationWriterContainerBase {
	private final S2OrientationBase _orientation;
	
	public S2OrientationValidator(S2Orientation orientation) {
		super(new ObservationWriterPrintStream(System.err));
		Objects.requireNonNull(orientation, "orientation");
		_orientation = (S2OrientationBase) orientation;
	}
	
	public boolean validateOffsets() {
		List<KeyValue<S2CompassDirection, IntegerCounter>> listTotals
			= new ArrayList<KeyValue<S2CompassDirection, IntegerCounter>>(8);
		ObservationWriter writer = getObservationWriter();
		for (S2CompassDirection direction : S2CompassDirection.AllDirs) {
			listTotals.add(new KeyValueContainer<S2CompassDirection, IntegerCounter>(
					direction, new IntegerCounter()));
		}		
		List<KeyValue<S2CompassDirection, S2Offset>> listOffsets
			= _orientation.getOffsetsList();
		listOffsets.forEach(kvp -> {
			KeyValue<S2CompassDirection, IntegerCounter> counter
				= KeyValueMethods.findFirstIn(kvp.getKey(), listTotals);
			counter.getValue().increment();
		});
		boolean bValid = true;
		for (KeyValue<S2CompassDirection, IntegerCounter> counter : listTotals) {
			int nTotal = counter.getValue().getValue();
			if (nTotal != 1) {
				writer.write(ObsLevel.ERROR, 
						"direction " + counter.getKey() + "has " + nTotal + " values");
				bValid = false;
			}
		}
		return bValid;
	}

}
