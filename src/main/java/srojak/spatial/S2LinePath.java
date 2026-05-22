/**
 * 
 */
package srojak.spatial;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Stephen
 *
 */
public interface S2LinePath {
	void setTieBreaker(BiFunction<S2Coords, S2Coords, S2Coords> fnTieBreaker);
	List<S2Coords> getCoordsOnLine(S2Line line, boolean bAllowDiagonal);
}
