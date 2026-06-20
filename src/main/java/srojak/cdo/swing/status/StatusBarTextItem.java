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
package srojak.cdo.swing.status;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * @author Stephen
 *
 */
public class StatusBarTextItem
		extends StatusBarItemBase {
	private final JTextField _text;

	/**
	 * @param columns
	 */
	public StatusBarTextItem(int columns) {
		super();
		_text = new JTextField(columns);
		postConstruct();
	}

	/**
	 * @param text
	 * @param columns
	 */
	public StatusBarTextItem(int columns, String text) {
		super();
		_text = new JTextField(text, columns);
		postConstruct();
	}

	private void postConstruct() {
		//_text.setHorizontalAlignment(LEFT);
		_text.setEditable(false);
		_text.setFocusable(false);
		_text.setOpaque(false);
	}

	@Override
	JComponent getComponent() {
		return _text;
	}

	@Override
	public boolean hasImage() {
		return false;
	}

	@Override
	public boolean hasText() {
		return true;
	}
	
	public String getText() {
		return _text.getText();
	}
	
	public void setText(String strText) {
		_text.setText(strText);
	}

	@Override
	public int getHorizontalAlignment() {
		return _text.getHorizontalAlignment();
	}

	@Override
	public void setHorizontalAlignment(int alignment) {
		_text.setHorizontalAlignment(alignment);
		
	}

	@Override
	public Border getBorder() {
		return _text.getBorder();
	}

	@Override
	public void setBorder(Border border) {
		_text.setBorder(border);		
	}
}
