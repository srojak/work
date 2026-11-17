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
import java.util.Objects;

import srojak.core.excepts.OperandsNotConformableException;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMathContextDyadicRule 
		implements MathContextDyadicRule {
	private DyadicOperandChoice _choiceLast;
	
	public AbstractMathContextDyadicRule() {
		_choiceLast = DyadicOperandChoice.UNASSIGNED;
	}

	@Override
	public boolean isPrecisionEqual(MathContextCarrying left, MathContextCarrying right) {
		Objects.requireNonNull(left, "left");
		Objects.requireNonNull(right, "right");
		MathContext ctxLeft = left.getMathContext();
		MathContext ctxRight = right.getMathContext();
		return ctxLeft.getPrecision() == ctxRight.getPrecision();
	}

	@Override
	public MathContext apply(MathContextCarrying left, MathContextCarrying right) {
		Objects.requireNonNull(left, "left");
		Objects.requireNonNull(right, "right");
		_choiceLast = DyadicOperandChoice.UNASSIGNED;
		MathContext ctxLeft = left.getMathContext();
		MathContext ctxRight = right.getMathContext();
		if (ctxLeft.equals(ctxRight)) {
			_choiceLast = DyadicOperandChoice.OPERANDS_EQUAL;
			return ctxLeft;
		}
		MathContext ctxUse = applyDissimilar(ctxLeft, ctxRight);
		if (ctxUse == null) {
			throw new OperandsNotConformableException("math contexts cannot be reconciled by rule");
		}
		if (_choiceLast == DyadicOperandChoice.UNASSIGNED) {
			if (ctxLeft.equals(ctxUse)) {
				_choiceLast = DyadicOperandChoice.LEFT_OPERAND;
			} else if (ctxRight.equals(ctxUse)) {
				_choiceLast = DyadicOperandChoice.RIGHT_OPERAND;
			} else {
				_choiceLast = DyadicOperandChoice.COMPUTED;
			}
		}
		return ctxUse;
	}

	@Override
	public DyadicOperandChoice lastResult() {
		return _choiceLast;
	}
	
	protected void setLastResult(DyadicOperandChoice choice) {
		Objects.requireNonNull(choice, "choice");
		_choiceLast = choice;
	}

	protected abstract MathContext applyDissimilar(MathContext ctxLeft, MathContext ctxRight);

}
