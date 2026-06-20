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

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.border.Border;

import srojak.cdo.swing.base.LabelCellRendererBase;
import srojak.core.TextRepresentation;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class SelectBorderListCellRenderer<E>
		extends LabelCellRendererBase<E> {
	private final Border _borderSelected;

	private static final Border _borderEmpty = BorderFactory.createEmptyBorder();
	
	/**
	 * @param repText
	 */
	public SelectBorderListCellRenderer(TextRepresentation repText, Border borderSelected) {
		super(repText);
		Objects.requireNonNull(borderSelected, "borderSelected");
		_borderSelected = borderSelected;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setTextFrom(value);
		if (isSelected) {
			setBorder(_borderSelected);
		} else {
			setBorder(_borderEmpty);
		}
		return this;
	}

}
