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

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import srojak.core.NameToken;
import srojak.core.events.StateChangeCodes;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingViewComponent
		extends NameTokenTagComponent
		implements StateChangeCodes {
    private final JScrollPane _scroll;
    private JComponent _view;

	/**
	 * @param tokenName
	 */
	public ScrollingViewComponent(NameToken tokenName) {
		super(tokenName);
		_scroll = new JScrollPane();
		_view = null;
	}

    public void setVerticalScrollBarPolicy(int policy) {
    	_scroll.setVerticalScrollBarPolicy(policy);
    }

    public void setHorizontalScrollBarPolicy(int policy) {
    	_scroll.setHorizontalScrollBarPolicy(policy);
    }
    
    protected JComponent getView() {
    	return _view;
    }
    
    protected void setView(JComponent view) {
		_view = view;
		_scroll.setViewportView(_view);
		// the primary content of a scroller is its view
		sendStateChange(SC_CONTENT, _view != null);
    }

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (_view != null) {
			_view.setFont(font);
		}
	}

	@Override
	public Font getFont() {
		return _view == null ? super.getFont() : _view.getFont();
	}

	@Override
	public boolean isFontSet() {
		return _view == null ? super.isFontSet() : _view.isFontSet();
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (_view != null) {
			_view.setForeground(fg);
		}
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (_view != null) {
			_view.setBackground(bg);
		}
	}

	@Override
	public Color getForeground() {
		return _view == null ? super.getForeground() : _view.getForeground();
	}

	@Override
	public boolean isForegroundSet() {
		return _view == null ? super.isForegroundSet() : _view.isForegroundSet();
	}

	@Override
	public Color getBackground() {
		return _view == null ? super.getBackground() : _view.getBackground();
	}

	@Override
	public boolean isBackgroundSet() {
		return _view == null ? super.isBackgroundSet() : _view.isBackgroundSet();
	}
}
