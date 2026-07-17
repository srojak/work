/**
 * 
 */
package srojak.spatial;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @author Stephen
 *
 */
public interface S2LinePath {
	void setWeightFunction(ToIntFunction<S2Coords> fnWeight);
	List<S2Coords> getCoordsOnLine(S2Line line, boolean bAllowDiagonal);
	List<S2UnitRay> getUnitVectorPath(S2Line line, boolean bAllowDiagonal);
}
