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
package srojak.cdo.swing.components;

import java.util.Comparator;
import java.util.Optional;

import javax.swing.ListCellRenderer;

import srojak.cdo.swing.functional.ObsLevelRenderer;
import srojak.core.NameToken;
import srojak.core.observe.ObsLevel;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ObsLevelComboBox 
		extends CommonItemComboBox<ObsLevel> {
	private static final ListCellRenderer<ObsLevel> _renderer;
	
	static {
		_renderer = new ObsLevelRenderer();
	}

	/**
	 * @param tokenName
	 */
	public ObsLevelComboBox(NameToken tokenName) {
		super(tokenName, ObsLevel.getAllKnown());
		setRenderer(_renderer);
		Optional<ObsLevel> widest = getDataAsStream().max(Comparator.comparingInt(e -> e.getName().length()));
		if (widest.isPresent()) {
			setPrototypeDisplayValue(widest.get());
		}
		setMaximumRowCount(getDataSize() / 2);
	}

}
