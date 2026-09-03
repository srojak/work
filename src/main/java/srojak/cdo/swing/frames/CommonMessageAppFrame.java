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
package srojak.cdo.swing.frames;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ScrollPaneConstants;

import srojak.cdo.swing.event.ActionListenerTextAreaCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaSelectAll;
import srojak.cdo.swing.panels.ScrollingMessagePanel;
import srojak.core.TextMessageRelay;
/**
 * @author Stephen
 *
 */
public class CommonMessageAppFrame
		extends CommonAppFrame
		implements Runnable {
    private final ScrollingMessagePanel _areaText;
    
    public CommonMessageAppFrame(String strAppName) {
    	super(strAppName);
        
        // put in text area
        _areaText = new ScrollingMessagePanel(ScrollingMessagePanel.PANEL_NAME, 10, 60);
        _areaText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        Box boxLower = getLowerBox();
        boxLower.add(_areaText, 0);
    }
         
    @SuppressWarnings("serial")
	protected void addTextMenu() {
		JMenu menu = new JMenu("Text");
		addMenu(menu);
		
		JMenuItem itemMenu = new JMenuItem("Select All");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListenerTextAreaSelectAll(_areaText));
		
		itemMenu = new JMenuItem("Copy");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListenerTextAreaCopy(_areaText));
		
		menu.addSeparator();
		itemMenu = new JMenuItem("Clear");
		menu.add(itemMenu);
		itemMenu.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_areaText.clearText();
			}
			
		});
    }
    
    protected ScrollingMessagePanel getTextArea() {
    	return _areaText;
    }
    
    protected TextMessageRelay getMessageOut() {
    	return _areaText;
    }
        
    public void relayText(String strText) {
    	Objects.requireNonNull(strText, "strText");
    	_areaText.writeln(strText);
    }
}
