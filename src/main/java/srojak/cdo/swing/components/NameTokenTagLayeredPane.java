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

import java.awt.Component;
import java.util.Objects;

import javax.swing.JLayeredPane;

import srojak.core.NameToken;
import srojak.core.NameTokenTagged;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NameTokenTagLayeredPane 
		extends JLayeredPane 
		implements NameTokenTagged {
	private final NameToken _token;

	/**
	 * 
	 */
	public NameTokenTagLayeredPane(NameToken tokenName) {
		super();
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

	public void addInLayer(Component component, int nLayer) {
		add(component, Integer.valueOf(nLayer));
	}
}
