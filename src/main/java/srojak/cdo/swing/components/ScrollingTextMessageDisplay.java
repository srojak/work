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

import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import srojak.cdo.swing.SimpleTextMessageComponent;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingTextMessageDisplay 
		extends ScrollingViewComponent
		implements SimpleTextMessageComponent {
    private final JTextArea _areaText;
    
    public static final NameToken PANEL_NAME = NameToken.classNameFactory(ScrollingTextMessageDisplay.class);
    
	/**
	 * @param tokenName
	 */
	public ScrollingTextMessageDisplay(NameToken tokenName, int nLines, int nColumns) {
		super(tokenName);
       _areaText = new JTextArea(nLines, nColumns);
	   	setView(_areaText);
	    _areaText.setEditable(false);
	   	
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

	@Override
	public void clearText() {
        _areaText.setText("");
	}

	@Override
	public void selectAllText() {
    	_areaText.selectAll();
	}

	@Override
	public String getSelectedString() {
        return _areaText.getSelectedText();
	}
}
