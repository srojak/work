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

import srojak.cdo.swing.SimpleTextMessageComponent;
import srojak.core.NameToken;
import srojak.core.events.StateChangeCodes;
import srojak.mantle.quants.TextBlockSize;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingTextAreaPanel
		extends ScrollingTextAreaPanelBase
		implements SimpleTextMessageComponent, StateChangeCodes {
    
    public static final NameToken PANEL_NAME = NameToken.classNameFactory(ScrollingTextAreaPanel.class);

    public ScrollingTextAreaPanel(NameToken tokenName, TextBlockSize size) {
    	super(tokenName, size);
    	_areaText.setEditable(false);
    }
    
    public ScrollingTextAreaPanel(NameToken tokenName, boolean isDoubleBuffered, TextBlockSize size) {
    	super(tokenName, isDoubleBuffered, size);
    	_areaText.setEditable(false);
    }

	@Override
	public void writeln(String strText) {
		Objects.requireNonNull(strText, "strText");
        _areaText.append(strText + "\n");
	}
	
	@Override
	public void flush() {
		// does not need to take action
	}
}
