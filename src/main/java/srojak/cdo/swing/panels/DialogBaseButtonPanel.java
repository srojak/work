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
package srojak.cdo.swing.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import srojak.cdo.GridBagConstraintsTool;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class DialogBaseButtonPanel
		extends NameTokenTagPanel {
	private GridBagConstraintsTool _builderGBC;
	
	public static final NameToken PANEL_NAME = NameToken.classNameFactory(DialogBaseButtonPanel.class);
	
	/**
	 * 
	 */
	public DialogBaseButtonPanel(NameToken tokenName) {
		super(tokenName, new GridBagLayout());
		_builderGBC = new GridBagConstraintsTool();
		_builderGBC.setAnchor(GridBagConstraints.EAST);
		_builderGBC.setWeights(1.0, 0.5);
		_builderGBC.setInsets(5, 0, 5, 5);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public DialogBaseButtonPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, new GridBagLayout(), isDoubleBuffered);
		_builderGBC = new GridBagConstraintsTool();
		_builderGBC.setAnchor(GridBagConstraints.EAST);
		_builderGBC.setWeights(1.0, 0.5);
	}

	public void addButton(JButton button, int nColumn) {
		_builderGBC.setGridPosition(nColumn, GridBagConstraints.RELATIVE);
		this.add(button, _builderGBC.snap());
	}
}
