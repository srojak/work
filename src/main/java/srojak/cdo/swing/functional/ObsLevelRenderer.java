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
package srojak.cdo.swing.functional;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import srojak.cdo.swing.CellRendererSettings;
import srojak.core.observe.ObsLevel;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ObsLevelRenderer
		extends JLabel 
		implements CellRendererSettings, ListCellRenderer<ObsLevel> {
	
	public ObsLevelRenderer() {
		super();
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ObsLevel> list, ObsLevel value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.getName() + " ");
		return this;
	}

	
}
