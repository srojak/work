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
package srojak.numerics;

import java.util.function.IntPredicate;

/**
 * @author Stephen
 *
 * Choices for a comparison between to values which can be compared.
 */
public enum OrderedComparison {
	EQ(r -> r == 0),
	NE(r -> r != 0),
	LT(r -> r < 0),
	LE(r -> r <= 0),
	GT(r -> r > 0),
	GE(r -> r >= 0);
	
	private final IntPredicate _predEval;
	
	private OrderedComparison(IntPredicate predicate) {
		_predEval = predicate;
	}
	
	/**
	 * evaluate an {@code int} result, as is produced by {@code compareTo( )} methods.
	 * @param result the value to evaluate.
	 * @return defined by the individual {@code ValueComparison} constants.
	 */
	public boolean evaluate(int result) {
		return _predEval.test(result);
	}
	
	public static boolean compareWithDelta(OrderedComparison comparison, double dA, double dB, double delta) {
		if (delta <= 0.0d) {
			throw new IllegalArgumentException("delta must be positive");
		}
		double dDiff = Math.abs(dA - dB);
		int nCompar = Double.compare(dDiff, delta);
		boolean bResult = false;
		switch (comparison) {
		case EQ:
			bResult = nCompar <= 0;
			break;
			
		case NE:
			bResult = nCompar > 0;
			break;
			
		case LT:
			bResult = dA < dB && nCompar > 0;
			break;
			
		case LE:
			bResult = dA < dB || nCompar <= 0;
			break;
			
		case GT:
			bResult = dA > dB && nCompar > 0;
			break;
			
		case GE:
			bResult = dA > dB || nCompar <= 0;
			break;
		}
		return bResult;
	}
}
