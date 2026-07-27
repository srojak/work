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
package srojak.psq.generate;

import java.util.Objects;

import srojak.numerics.IRandomSource;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2RandomMover;
import srojak.spatial.S2Surface;

/**
 * @author Stephen
 *
 */
public class PSqCommonGenTool
		implements S2Surface, IRandomSource {
	private final S2Surface _surface;
	private final IRandomSource _rand;
	private final S2RandomMover _mover;
	
	public PSqCommonGenTool(S2Surface surface, IRandomSource sourceRandom) {
		Objects.requireNonNull(surface, "surface");
		Objects.requireNonNull(sourceRandom, "sourceRandom");
		_surface = surface;
		_rand = sourceRandom;
		_mover = new S2RandomMover(_rand, _surface);
	}
	
	@Override
	public S2Orientation getOrientation() {
		return _surface.getOrientation();
	}

	@Override
	public S2FieldSize getFieldSize() {
		return _surface.getFieldSize();
	}

	@Override
	public Boolean genBoolean() {
		return _rand.genBoolean();
	}

	@Override
	public int genIntInRange(int nBound) {
		return _rand.genIntInRange(nBound);
	}

	@Override
	public double genDouble() {
		return _rand.genDouble();
	}

	@Override
	public double genGaussian() {
		return _rand.genGaussian();
	}

	@Override
	public double genExponential(double dLambda) {
		return _rand.genExponential(dLambda);
	}
	
	public S2RandomMover getRandomMover() {
		return _mover;
	}
}
