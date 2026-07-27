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
public class GridBagConstraintsTool {
	private GridBagConstraints _constraints;
	
	public GridBagConstraintsTool() {
		_constraints = new GridBagConstraints();
	}
	
	/**
	 * Makes a copy of the current state of the tool.
	 * @return A {@code GridBagConstraints} object containing the current settings.
	 */
	public GridBagConstraints snap() {
		return (GridBagConstraints) _constraints.clone();
	}
	
	/**
	 * Sets the number of cells for a component to occupy.
	 * @param nWidth The width required for the component.
	 * @param nHeight The height required for the component.
	 */
	public void setGridSize(int nWidth, int nHeight) {
		_constraints.gridwidth = nWidth;
		_constraints.gridheight = nHeight;
	}
	
	/**
	 * Sets the cell position for the next component.
	 * Either of these values can be GridBagConstraints.RELATIVE.
	 * @param nX The cell column for the leading left edge of the component.
	 * @param nY The cell row for the top edge  of the component.
	 */
	public void setGridPosition(int nX, int nY) {
		_constraints.gridx = nX;
		_constraints.gridy = nY;
	}
	
	/**
	 * Controls where to place the component when the component is smaller than the space
	 * allowed for it.
	 * 
	 * @param anchor The anchor constant to use.
	 *  
	 * @see java.awt.GridBagConstraints.anchor
	 */
	public void setAnchor(int anchor) {
		_constraints.anchor = anchor;
	}
	
	/**
	 * Controls how to resize the component when the display area is larger than the component's
	 * requested size.
	 * @param nFill The specifier for how to resize the component.
	 * Allowable values
	 * <ul>
	 * <li>NONE: Do not resize the component.
	 * <li>HORIZONTAL: Make the component wide enough to fill its display area horizontally, but do not change its height.
	 * <li>VERTICAL: Make the component tall enough to fill its display area vertically, but do not change its width.
	 * <li>BOTH: Make the component fill its display area entirely.
	 * </ul>
	 *  
	 * @see java.awt.GridBagConstraints.fill
	 */
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
