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

import java.util.Random;
/**
 * @author Stephen
 *
 */
public class RandomSource
		implements IRandomSource {
    private Random _generator;
    private DoublePrecisionComparer _comparer;
    
    public RandomSource() {
    	_generator = new Random();
    	_comparer = DoublePrecisionComparer.DEFAULT_COMPARER;
    }
    
    public void setSeed(long lnSeed) {
    	_generator.setSeed(lnSeed);
    }

	@Override
	public Boolean genBoolean() {
		return _generator.nextBoolean();
	}

	@Override
	public int genIntInRange(int nBound) {
		return _generator.nextInt(nBound);
	}

	@Override
	public double genDouble() {
		return _generator.nextDouble();
	}

	@Override
	public double genGaussian() {
		return _generator.nextGaussian();
	}

	@Override
	public double genExponential(double dLambda) {
		if (_comparer.compare(dLambda, 0.0d) <= 0) {
			throw new IllegalArgumentException("dLambda must be positive");
		}
		return dLambda * Math.exp(- dLambda * _generator.nextDouble());
	}
}
