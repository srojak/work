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
package srojak.numerics.extmath;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class MatchContextDyadicLeastDecimalsRule 
		extends AbstractMathContextDyadicRule {
	private final RoundingMode _modeFallback;
	
	public MatchContextDyadicLeastDecimalsRule(RoundingMode modeIfConflicts) {
		Objects.requireNonNull(modeIfConflicts, "modeIfConflicts");
		_modeFallback = modeIfConflicts;
	}

	@Override
	protected MathContext applyDissimilar(MathContext ctxLeft, MathContext ctxRight) {
		int precLeft = ctxLeft.getPrecision();
		int precRight = ctxRight.getPrecision();
		int precOut = precLeft;
		if (precLeft == 0) {
			if (precRight != 0) {
				precOut = precRight;
			}
		} else if (precRight != 0 && precRight < precLeft) {
			precOut = precRight;
		}
		RoundingMode rmode = ctxLeft.getRoundingMode();
		if (rmode != ctxRight.getRoundingMode()) {
			rmode = _modeFallback;
		}
		return new MathContext(precOut, rmode);
	}

}
