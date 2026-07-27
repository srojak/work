/**
 * 
 */
package srojak.psq.swing;

import java.util.Objects;

import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;

/**
 * @author Stephen
 *
 */
public class PlaneSquarePainterBase<S extends PlaneSquare> {
	protected final PlaneSquareGrid<S> _grid;
	protected final PlaneSquareScalerTool _scaler;

	public PlaneSquarePainterBase(PlaneSquareGrid<S> grid, PlaneSquareScalerTool scaler) {
		Objects.requireNonNull(grid, "grid");
		Objects.requireNonNull(scaler, "scaler");
		_grid = grid;
		_scaler = scaler;
	}
}
