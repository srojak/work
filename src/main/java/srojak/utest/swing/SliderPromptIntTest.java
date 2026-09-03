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
package srojak.utest.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import srojak.cdo.swing.panels.ScrollingMessagePanel;
import srojak.core.NameToken;
import srojak.gui.SliderPromptInteger;
import srojak.gui.cxs.IntegerLabelTable;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalInt;
/**
 * @author Stephen
 *
 */
public class SliderPromptIntTest
		implements Runnable {
	private JFrame _frameMain;
    private ScrollingMessagePanel _areaText;
	
	public SliderPromptIntTest() {
		_frameMain = new JFrame("SliderPrompt");
        _frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container ctnPanel = _frameMain.getContentPane();
        
        // put in text area
        _areaText = new ScrollingMessagePanel(ScrollingMessagePanel.PANEL_NAME, 10, 60);
        _areaText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        ctnPanel.add(_areaText, BorderLayout.SOUTH);
        SliderValueChangeListener ls = new SliderValueChangeListener(_areaText);
        
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
        
        SliderPromptInteger sp = new SliderPromptInteger(NameToken.factory("ElementA"),
        		new IntervalInt(IntervalType.CLOSED, 5, 15));
        sp.setPrompt("Element A");
        sp.setPreferredSliderSize(new Dimension(200, 100));
        sp.setSliderTicks(5, 1);
        panelCenter.add(sp);
        IntegerLabelTable tableIC = new IntegerLabelTable();
        tableIC.addLabel(5, new JLabel("5"));
        tableIC.addLabel(10, new JLabel("10"));
        tableIC.addLabel(15, new JLabel("15"));
        sp.setLabelTable(tableIC);
        sp.addValueChangeListener(ls);
        
        sp = new SliderPromptInteger(NameToken.factory("ElementB"),
        		new IntervalInt(IntervalType.CLOSED, 3, 6));
         sp.setPrompt("Element B");
        sp.setSliderTicks(1);
        panelCenter.add(sp);
        sp.addValueChangeListener(ls);
        
        sp = new SliderPromptInteger(NameToken.factory("ElementC"),
        		new IntervalInt(IntervalType.CLOSED, 1, 100));
        sp.setPreferredWidth(500);
        sp.setPrompt("Element C");
        sp.setSliderTicks(20, 5);
        panelCenter.add(sp);
        sp.addValueChangeListener(ls);
        
        _frameMain.add(panelCenter, BorderLayout.CENTER);
	}
	
	@Override
	public void run() {
        _frameMain.pack();
        _frameMain.setVisible(true);		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SliderPromptIntTest test = new SliderPromptIntTest();
        // must run on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(test);
	}

}
