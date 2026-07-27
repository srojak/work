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
import java.util.Objects;

import javax.swing.JList;

import srojak.cdo.ColorPair;
import srojak.cdo.swing.lists.LabelListCellRendererBase;
import srojak.core.TextRepresentation;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorListCellRenderer<E>
		extends LabelListCellRendererBase<E> {
	private final ColorPair _colorsUnselected;
	private final ColorPair _colorsSelected;

	/**
	 * 
	 */
	public ColorListCellRenderer(TextRepresentation repText,
			ColorPair colorsUnselected, ColorPair colorsSelected) {
		super(repText);
		Objects.requireNonNull(colorsUnselected, "colorsUnselected");
		Objects.requireNonNull(colorsSelected, "colorsSselected");
		_colorsUnselected = colorsUnselected;
		_colorsSelected = colorsSelected;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setTextFrom(value);
		ColorPair pair = _colorsUnselected;
		if (isSelected) {
			pair = _colorsSelected;
		}
		setBackground(pair.getBackgroundColor());
		setForeground(pair.getForegroundColor());
		return this;
	}

}
