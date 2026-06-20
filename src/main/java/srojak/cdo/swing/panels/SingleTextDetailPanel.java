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

import java.awt.Color;
import java.awt.LayoutManager;
import java.util.Objects;

import javax.swing.JTextField;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class SingleTextDetailPanel
		extends NameTokenTagPanel {
	private JTextField _txField;

	/**
	 * @param tokenName
	 */
	public SingleTextDetailPanel(NameToken tokenName, int nColumns) {
		super(tokenName);
		_txField = new JTextField(nColumns);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param layout
	 */
	public SingleTextDetailPanel(NameToken tokenName, LayoutManager layout, int nColumns) {
		super(tokenName, layout);
		_txField = new JTextField(nColumns);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public SingleTextDetailPanel(NameToken tokenName, boolean isDoubleBuffered, int nColumns) {
		super(tokenName, isDoubleBuffered);
		_txField = new JTextField(nColumns);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public SingleTextDetailPanel(NameToken tokenName, LayoutManager layout,
			boolean isDoubleBuffered, int nColumns) {
		super(tokenName, layout, isDoubleBuffered);
		_txField = new JTextField(nColumns);
		postConstruct();
	}

	private void postConstruct() {
		_txField.setPreferredSize(_txField.getMinimumSize());
		_txField.setEditable(false);
		add(_txField);
	}
	
	public void setTextBackground(Color color) {
		_txField.setBackground(color);
	}
	
	public void clearText() {
		_txField.setText("");
	}
	
	public void setText(String strText) {
		Objects.requireNonNull(strText, "strText");
		_txField.setText(strText);
	}
}
