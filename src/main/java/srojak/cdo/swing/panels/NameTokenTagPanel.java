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

import java.awt.LayoutManager;
import java.util.Objects;

import javax.swing.JPanel;

import srojak.core.NameToken;
import srojak.core.NameTokenTagged;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NameTokenTagPanel
		extends JPanel 
		implements NameTokenTagged {
	private final NameToken _token;

	/**
	 * 
	 */
	public NameTokenTagPanel(NameToken tokenName) {
		super();
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
	}

	/**
	 * @param layout
	 */
	public NameTokenTagPanel(NameToken tokenName, LayoutManager layout) {
		super(layout);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
	}

	/**
	 * @param isDoubleBuffered
	 */
	public NameTokenTagPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public NameTokenTagPanel(NameToken tokenName, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
	}

	@Override
	public NameToken getNameTag() {
		return _token;
	}

	@Override
	public boolean isNameTagEqual(NameToken token) {
		return _token.equals(token);
	}


	@Override
	public boolean isNameTagEqual(String strName) {
		return _token.isNameEqual(strName);
	}
}
