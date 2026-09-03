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

import javax.swing.JComponent;
import javax.swing.JPanel;

import srojak.cdo.swing.interact.ComponentEnabledStateManager;
import srojak.cdo.swing.interact.ComponentEnablingFacade;
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
	private final ComponentEnabledStateManager _mgrEnabled;

	/**
	 * 
	 */
	public NameTokenTagPanel(NameToken tokenName) {
		super();
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_mgrEnabled = new ComponentEnabledStateManager(this);
	}

	/**
	 * @param layout
	 */
	public NameTokenTagPanel(NameToken tokenName, LayoutManager layout) {
		super(layout);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_mgrEnabled = new ComponentEnabledStateManager(this);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public NameTokenTagPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_mgrEnabled = new ComponentEnabledStateManager(this);
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public NameTokenTagPanel(NameToken tokenName, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_mgrEnabled = new ComponentEnabledStateManager(this);
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

	@Override
	public String getName() {
		String strName = super.getName();
		if (strName == null) {
			strName = _token.getName();
		}
		return strName;
	}
	
	protected ComponentEnabledStateManager getEnabledStateManager() {
		return _mgrEnabled;
	}
	
	public ComponentEnablingFacade findEnablingFacadeForComponent(JComponent component) {
		return _mgrEnabled.findFacadeForComponent(component);
	}
	
	public ComponentEnablingFacade createEnablingFacadeFor(JComponent component) {
		return new ComponentEnablingFacade(component);
	}
	
	public void addChild(ComponentEnablingFacade facade) {
		_mgrEnabled.addChild(facade);
	}
	
	public void addChild(ComponentEnablingFacade facade, Object constraints) {
		_mgrEnabled.addChild(facade, constraints);
	}
	
	public void removeChild(ComponentEnablingFacade facade) {
		_mgrEnabled.removeChild(facade);
	}
}
