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

import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import srojak.cdo.swing.interact.PropertyRepeatListener;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingViewPanel 
		extends NameTokenTagCommonEventPanel {
    private final JScrollPane _scroll;
    private PropertyRepeatListener _repeater;

	/**
	 * @param tokenName
	 */
	public ScrollingViewPanel(NameToken tokenName) {
		super(tokenName);
		_scroll = new JScrollPane();
		_repeater = null;
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ScrollingViewPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_scroll = new JScrollPane();
		_repeater = null;
		postConstruct();
	}

	private void postConstruct() {
		// the scrolling pane fills the panel
        add(_scroll);
	}
	
	public void setScrollerPreferredSize(Dimension size) {
		_scroll.setPreferredSize(size);
	}

    public void setVerticalScrollBarPolicy(int policy) {
    	_scroll.setVerticalScrollBarPolicy(policy);
    }

    public void setHorizontalScrollBarPolicy(int policy) {
    	_scroll.setHorizontalScrollBarPolicy(policy);
    }
    
    protected void setView(JComponent view) {
		Objects.requireNonNull(view, "view");
		if (_repeater != null) {
			this.removePropertyChangeListener(_repeater);
		}
		_scroll.setViewportView(view);
		_repeater = new PropertyRepeatListener(view);
		this.addPropertyChangeListener(_repeater);
		view.setFont(getFont());
		view.setBackground(getBackground());
		view.setForeground(getForeground());
    }
}
