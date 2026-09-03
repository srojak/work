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

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingViewPanel 
		extends NameTokenTagPanel {
    private final JScrollPane _scroll;

	/**
	 * @param tokenName
	 */
	public ScrollingViewPanel(NameToken tokenName) {
		super(tokenName);
		_scroll = new JScrollPane();
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ScrollingViewPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_scroll = new JScrollPane();
		postConstruct();
	}

    public void setVerticalScrollBarPolicy(int policy) {
    	_scroll.setVerticalScrollBarPolicy(policy);
    }

    public void setHorizontalScrollBarPolicy(int policy) {
    	_scroll.setHorizontalScrollBarPolicy(policy);
    }
    
    protected void setView(JComponent view) {
		Objects.requireNonNull(view, "view");
		_scroll.setViewportView(view);
    }

	private void postConstruct() {
		// the scrolling pane fills the panel
        add(_scroll);
	}
}
