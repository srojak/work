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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import srojak.cdo.TextMessageComponent;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingMessagePanel
		extends NameTokenTagPanel
		implements TextMessageRelay, TextMessageComponent {
    private JTextArea _areaText;
    private JScrollPane _scroll;
    
    public static final NameToken PANEL_NAME = NameToken.classNameFactory(ScrollingMessagePanel.class);

    public ScrollingMessagePanel(NameToken tokenName, int nLines, int nColumns) {
    	super(tokenName);
        _areaText = new JTextArea(nLines, nColumns);
        _scroll = new JScrollPane(_areaText);
    	postConstruct();
    }
    
    public ScrollingMessagePanel(NameToken tokenName, boolean isDoubleBuffered, int nLines, int nColumns) {
    	super(tokenName, isDoubleBuffered);
        _areaText = new JTextArea(nLines, nColumns);
        _scroll = new JScrollPane(_areaText);
    	postConstruct();
   }
    
    private void postConstruct() {
         _areaText.setEditable(false);
   	
        _scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        _scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	
        add(_scroll);
    }

    public void setVerticalScrollBarPolicy(int policy) {
    	_scroll.setVerticalScrollBarPolicy(policy);
    }

    public void setHorizontalScrollBarPolicy(int policy) {
    	_scroll.setHorizontalScrollBarPolicy(policy);
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
    public String getSelectedString() {
        return _areaText.getSelectedText();
    }
    
	@Override
    public void selectAllText() {
    	_areaText.selectAll();
    }

}
