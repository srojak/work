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
package srojak.cdo.swing.base;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ScrollPaneConstants;

import srojak.cdo.swing.AppFrameContainer;
import srojak.cdo.swing.ExitControl;
import srojak.cdo.swing.event.ActionListenerTextAreaCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaSelectAll;
import srojak.cdo.swing.functional.AppFrameExitControl;
import srojak.cdo.swing.panels.ScrollingMessagePanel;
import srojak.cdo.swing.status.StatusBar;
import srojak.core.TextMessageRelay;
/**
 * @author Stephen
 *
 */
public class CommonMessageAppFrame
		implements Runnable {
	private final AppFrameContainer _ctnrFrame;
	private final Container _ctnContent;
	private final JMenuBar _barMenu;
	private final StatusBar _barStatus;
    private final ScrollingMessagePanel _areaText;
    private final AppFrameExitControl _ctlExit;
    
    public CommonMessageAppFrame(String strAppName) {
		JFrame frameMain = new JFrame(strAppName);
		_ctnrFrame = new AppFrameContainer(frameMain);
		_ctnContent = frameMain.getContentPane();
		_ctlExit = new AppFrameExitControl();
		_ctlExit.attach(frameMain);
         Box boxLower = Box.createVerticalBox();
         _ctnContent.add(boxLower, BorderLayout.SOUTH);
        
        _barMenu = new JMenuBar();
        frameMain.setJMenuBar(_barMenu);
        
        // put in text area
        _areaText = new ScrollingMessagePanel(ScrollingMessagePanel.PANEL_NAME, 10, 60);
        _areaText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        boxLower.add(_areaText);
        
        // put in status bar
        _barStatus = new StatusBar(StatusBar.ClassToken);
        boxLower.add(_barStatus);
    }
    
    protected ExitControl getExitControl() {
    	return _ctlExit;
    }
    
    protected void setIconImage(Image image) {
    	_ctnrFrame.setIconImage(image);
    }
    
    protected void addMenu(JMenu menu) {
    	_barMenu.add(menu);
    }
    
    @SuppressWarnings("serial")
	protected JMenuItem createExitMenuItem() {
    	JMenuItem itemMenu = new JMenuItem("Exit");
    	itemMenu.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_ctlExit.requestClose();				
			}
    	});
    	return itemMenu;
    }
    
    @SuppressWarnings("serial")
	protected void addTextMenu() {
		JMenu menu = new JMenu("Text");
		_barMenu.add(menu);
		
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
    
    protected StatusBar getStatusBar() {
    	return _barStatus;
    }
    
    protected ScrollingMessagePanel getTextArea() {
    	return _areaText;
    }
    
    protected TextMessageRelay getMessageOut() {
    	return _areaText;
    }
    
    protected void repaintContent() {
    	_ctnContent.repaint();
    }
    
    public void relayText(String strText) {
    	Objects.requireNonNull(strText, "strText");
    	_areaText.writeln(strText);
    }
    
    public AppFrameContainer getAppFrameContainer() {
    	return _ctnrFrame;
    }
    
   	protected <T extends Window> T createWithParentFrame(Function<JFrame, T> function) {
   		return _ctnrFrame.createWithParentFrame(function);
   	}
    
    protected void addComponentToCenter(JComponent component) {
        _ctnContent.add(component, BorderLayout.CENTER);
    }
    
    protected void addComponentToTop(JComponent component) {
        _ctnContent.add(component, BorderLayout.NORTH);
    }
    
    protected void addComponentToLeft(JComponent component) {
        _ctnContent.add(component, BorderLayout.WEST);
    }
    
    protected void addComponentToRight(JComponent component) {
         _ctnContent.add(component, BorderLayout.EAST);
    }
    
    protected int showOpenFileDialog(JFileChooser chooser) {
    	return _ctnrFrame.showOpenFileDialog(chooser);
    }
    
    protected int showSaveFileDialog(JFileChooser chooser) {
    	return _ctnrFrame.showSaveFileDialog(chooser);
    }
    
    protected void doOnceRunning() {
    	// base class method does nothing
    }

	@Override
	public void run() {
		_ctnrFrame.makeVisible();
		doOnceRunning();
	}

}
