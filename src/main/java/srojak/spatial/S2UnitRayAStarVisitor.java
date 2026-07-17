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

import java.util.LinkedList;
import java.util.List;

import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class S2UnitRayAStarVisitor
		extends S2CoordAStarVisitorBase<List<S2UnitRay>> {

	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = S2UnitRayAStarVisitor.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param surface
	 */
	public S2UnitRayAStarVisitor(S2Surface surface) {
		super(_swDebugClass, surface);
	}

	@Override
	protected void visitLocation(int nSequence, S2UnitRay ray, List<S2UnitRay> arg)
			throws InvalidLocationException {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "seq=" + nSequence + ", ray=" + ray);
		arg.add(ray);
	}
	
	public List<S2UnitRay> expand(S2Segment segment) 
			throws InvalidLocationException {
		setupUsing(segment);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "segment " + segment);
		LinkedList<S2UnitRay> list = new LinkedList<S2UnitRay>();
		walk(list, true);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "list of " + list.size() + " unit rays");
		return list;
	}
	
	public List<S2UnitRay> expand(S2OffsetRay ray) 
			throws InvalidLocationException {
		setupUsing(ray);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "ray " + ray);
		LinkedList<S2UnitRay> list = new LinkedList<S2UnitRay>();
		walk(list, true);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "list of " + list.size() + " unit rays");
		return list;
	}
}
