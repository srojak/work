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
package srojak.numerics.weighted;

import java.util.List;

import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.IRandomSource;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public interface DoubleWeightedList 
		extends List<DoubleWeighted> {

	public default void assignWeights() {
		double dSum = 0.0d;
		for (DoubleWeighted item : this) {
			dSum += item.getWeightFactor();
		}
		for (DoubleWeighted item : this) {
			item.computeWeight(dSum);
		}
	}
	
	public default DoubleWeighted select(IRandomSource rand) {
		double dRoll = rand.genDouble();
		DoublePrecisionComparer comparer = DoubleWeightedObject.getComparer();
		for (DoubleWeighted item : this) {
			double dWeight = item.getWeight();
			if (comparer.compare(dRoll, OrderedComparison.LE, dWeight)) {
				return item;
			}
			dRoll -= dWeight;
		}
		return null;
	}
	
}
