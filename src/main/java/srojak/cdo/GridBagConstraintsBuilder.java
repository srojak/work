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
package srojak.cdo;

import java.awt.*;
/**
 * @author Stephen
 *
 */
public class GridBagConstraintsBuilder {
	private GridBagConstraints _constraints;
	
	public GridBagConstraintsBuilder() {
		_constraints = new GridBagConstraints();
	}
	
	public GridBagConstraints getConstraints() {
		return (GridBagConstraints) _constraints.clone();
	}
	
	public void setGridSize(int nWidth, int nHeight) {
		_constraints.gridwidth = nWidth;
		_constraints.gridheight = nHeight;
	}
	
	public void setGridPosition(int nX, int nY) {
		_constraints.gridx = nX;
		_constraints.gridy = nY;
	}
	
	public void setAnchor(int anchor) {
		_constraints.anchor = anchor;
	}
	
	public void setFill(int nFill) {
		_constraints.fill = nFill;
	}
	
	public void setHorizontalWeight(double dWeight) {
		_constraints.weightx = dWeight;
	}
	
	public void setVerticalWeight(double dWeight) {
		_constraints.weighty = dWeight;
	}
	
	public void setWeights(double dX, double dY) {
		_constraints.weightx = dX;
		_constraints.weighty = dY;
	}
	
	public void setPadding(int padX, int padY) {
		_constraints.ipadx = padX;
		_constraints.ipady = padY;
	}
	
	public Insets getInsets() {
		return _constraints.insets;
	}
	
	public void setInsets(int dist) {
		_constraints.insets = new Insets(dist, dist, dist, dist);
	}
	
	public void setInsets(int top, int left, int bottom, int right) {
		_constraints.insets = new Insets(top, left, bottom, right);
	}
}
