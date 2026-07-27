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

import srojak.cdo.swing.models.ListSelectChoiceModel;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ListSelectChoiceGroupBoxPanel
		extends NamedChoiceGroupBoxPanel {

	public static final NameToken PANEL_NAME 
			= NameToken.classNameFactory(ListSelectChoiceGroupBoxPanel.class);

	/**
	 * @param tokenName
	 * @param itemsAcross
	 */
	public ListSelectChoiceGroupBoxPanel(NameToken tokenName, int itemsAcross) {
		super(tokenName, itemsAcross);
		setModel(new ListSelectChoiceModel());
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 * @param itemsAcross
	 */
	public ListSelectChoiceGroupBoxPanel(NameToken tokenName, boolean isDoubleBuffered, int itemsAcross) {
		super(tokenName, isDoubleBuffered, itemsAcross);
		setModel(new ListSelectChoiceModel());
	}

}
