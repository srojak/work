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
package srojak.psq.swing.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.DataComponent;
import srojak.cdo.swing.DxButtonModelPublisher;
import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.cdo.swing.panels.GroupBoxPanel;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class PlaneSquareOptionsPanel
		extends GroupBoxPanel 
		implements DxButtonModelPublisher, DataComponent {
	private final List<DxButtonModelFacade> _listButtonModels;
	private final JCheckBox _ckShowScale;
	private final JCheckBox _ckEnable;
	private final ButtonModel _modelShowScale;
	private final ButtonModel _modelEnable;

	public static final NameToken ClassToken;
	public static final NameToken CK_SHOW_SCALE;
	public static final NameToken CK_ENABLE_GRID;
	
	static {
		Class<?> classThis = PlaneSquareOptionsPanel.class;
		ClassToken = NameToken.classNameFactory(classThis);
		CK_SHOW_SCALE = NameToken.factory(ClassToken, "ckShowScale");
		CK_ENABLE_GRID = NameToken.factory(ClassToken, "ckEnable");
	}

	/**
	 * @param tokenName
	 */
	public PlaneSquareOptionsPanel(NameToken tokenName) {
		super(tokenName);
		_listButtonModels = new LinkedList<DxButtonModelFacade>();
		_ckShowScale = new JCheckBox("Show scale info");
		_modelShowScale = _ckShowScale.getModel();
		_ckEnable = new JCheckBox("Enable gridlines");
		_modelEnable = _ckEnable.getModel();
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public PlaneSquareOptionsPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_listButtonModels = new LinkedList<DxButtonModelFacade>();
		_ckShowScale = new JCheckBox("Show scale info");
		_modelShowScale = _ckShowScale.getModel();
		_ckEnable = new JCheckBox("Enable gridlines");
		_modelEnable = _ckEnable.getModel();
		postConstruct();
	}
	
	private void postConstruct() {
		setLayout(new GridBagLayout());
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(GridBagConstraints.HORIZONTAL);
		toolGBC.setGridSize(1,  1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setGridPosition(0, 0);
		add(_ckShowScale, toolGBC.snap());
		_listButtonModels.add(new DxButtonModelFacade(CK_SHOW_SCALE, _modelShowScale));
		
		toolGBC.setGridPosition(0, 1);
		add(_ckEnable, toolGBC.snap());
		_listButtonModels.add(new DxButtonModelFacade(CK_ENABLE_GRID, _modelEnable));
		
		Dimension dmSizeItem = _ckEnable.getPreferredSize();
		Dimension dmSizePanel = new Dimension(dmSizeItem.width * 2, dmSizeItem.height * 4);
		setPreferredSize(dmSizePanel);
	}

	@Override
	public List<DxButtonModelFacade> getButtonModelList() {
		return _listButtonModels;
	}

	public ButtonModel getEnableCheckBoxModel() {
		return _modelEnable;
	}
}
