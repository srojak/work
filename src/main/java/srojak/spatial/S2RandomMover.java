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

import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.IRandomSource;
import srojak.numerics.compass.CompassDegrees;

/**
 * @author Stephen
 *
 */
public class S2RandomMover {
	private final IRandomSource _rand;
	private final S2Orientation _orient;
	private final S2FieldSize _szField;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = S2RandomMover.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public S2RandomMover(IRandomSource sourceRandom, S2Surface surface) {
		Objects.requireNonNull(sourceRandom, "sourceRandom");
		Objects.requireNonNull(surface, "surface");
		_rand = sourceRandom;
		_orient = surface.getOrientation();
		_szField = surface.getFieldSize();
	}
	
	public S2FieldSize getFieldSize() {
		return _szField;
	}
	
	public S2CompassDirection getRandomDirection() {
		int nRoll = _rand.genIntInRange(8);
		return S2CompassDirection.AllDirs.get(nRoll);
	}
	
	public S2CompassDirection getRandomCardinalDirection() {
		int nRoll = _rand.genIntInRange(4);
		return S2CompassDirection.CardinalDirs.get(nRoll);
	}
	
	public S2CompassDirection getRandomDirectionInQuarterArc(S2CompassDirection direction) {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "direction = " + direction.getAbbrev());
		double dRoll = _rand.genGaussian();
		// scale so that 45 degrees = 4 standard devs
		CompassDegrees degrees1 = direction.getDegrees();
		CompassDegrees degrees = degrees1.addAndNormalize((float) (11.25d * dRoll));
		_swDebugClass.write(ObsLevel.DEBUG, () -> "gen degrees = " + degrees);
		S2CompassDirection dirReturn = S2CompassDirection.findDirectionFor(degrees);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "direction = " + dirReturn);
		return dirReturn;
	}
	
	public S2CompassDirection getRandomDirectionInHalfArc(S2CompassDirection direction) {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "direction = " + direction.getAbbrev());
		double dRoll = _rand.genGaussian();
		// scale so that 90 degrees = 4 standard devs
		CompassDegrees degrees1 = direction.getDegrees();
		CompassDegrees degrees = degrees1.addAndNormalize((float) (22.5d * dRoll));
		_swDebugClass.write(ObsLevel.DEBUG, () -> "gen degrees = " + degrees);
		S2CompassDirection dirReturn = S2CompassDirection.findDirectionFor(degrees);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "direction = " + dirReturn);
		return dirReturn;
	}
	
	public S2Offset moveRandomDistance(S2CompassDirection direction, double dLambda, int nFloor) {
		Objects.requireNonNull(direction, "direction");
		double dRoll = _rand.genExponential(dLambda);
		return _orient.offset(direction, nFloor + (int) Math.floor(dRoll));
	}
	
	public S2Offset moveRandomDistance(S2CompassDirection direction, double dLambda) {
		return moveRandomDistance(direction, dLambda, 1);
	}
	
	public S2Offset moveRandomDirection(int nDistance) {
		S2CompassDirection dir = getRandomDirection();
		return _orient.offset(dir, nDistance);
	}
	
	public S2Coords walkOneSquare(S2Coords coordsFrom, S2CompassDirection direction) {
		Objects.requireNonNull(coordsFrom, "coordsFrom");
		Objects.requireNonNull(direction, "direction");
		S2Offset offset = _orient.offsetByOne(direction);
		return coordsFrom.getNewLocationFrom(offset);
	}
}
