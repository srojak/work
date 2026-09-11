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
package srojak.cdo.swing.lists;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import srojak.cdo.swing.CellRendererSettings;
import srojak.cdo.swing.UIManagerKeys;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class LabelListCellRendererBase<E>
	extends JLabel 
	implements CellRendererSettings, ListCellRenderer<E>, UIManagerKeys {

	/**
	 * 
	 */
	public LabelListCellRendererBase() {
		super();
		setOpaque(true);
	}

	/**
	 * @param image
	 */
	public LabelListCellRendererBase(Icon image) {
		super(image);
		setOpaque(true);
	}

	/**
	 * @param image
	 * @param horizontalAlignment
	 */
	public LabelListCellRendererBase(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		setOpaque(true);
	}
	
	protected void setTextFrom(E item) {
		setText(item.toString());
	}
	
	protected Color getHighlightColor() {
		return UIManager.getColor(List_Selection);
	}
	
	protected abstract void display(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus);

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		display(list, value, index, isSelected, cellHasFocus);
		return this;
	}
}
