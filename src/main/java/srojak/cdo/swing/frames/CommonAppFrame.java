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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import srojak.cdo.containers.ResourceImage;
import srojak.cdo.swing.ExitControl;
import srojak.cdo.swing.functional.AppFrameExitControl;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.cdo.swing.status.StatusBar;
import srojak.cdo.swing.status.StatusBarTextItem;
import srojak.core.NameToken;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.result.XResult;

/**
 * @author Stephen
 *
 */
public class CommonAppFrame 
		implements Runnable {
	private final AppFrameContainer _ctnrFrame;
	private final Container _ctnContent;
	private final JMenuBar _barMenu;
	private final StatusBar _barStatus;
	private final AppFrameExitControl _ctlExit;
    private final ResourceImage _resCommonIcon;
    private final NameTokenTagPanel _panelLower;
    private final FlagsInt _flagsStart;
    
    public static final int START_MINSIZE_CURSIZE = 0x1;
    public static final String FRAME_NAME = "ApplicationFrame";
	public static final NameToken ClassToken;
	
	static {
		Class<?> classThis = CommonAppFrame.class;
		ClassToken = NameToken.classNameFactory(classThis);		
	}

	/**
	 * 
	 */
	public CommonAppFrame(String strAppName) {
		Objects.requireNonNull(strAppName, "strAppName");
		if (strAppName.isBlank()) {
			throw new IllegalArgumentException("strAppName is blank");
		}
		JFrame frameMain = new JFrame(strAppName);
		frameMain.setName(FRAME_NAME);
		_ctnrFrame = new AppFrameContainer(frameMain);
		_ctnContent = frameMain.getContentPane();
		_ctlExit = new AppFrameExitControl();
		_ctlExit.attach(frameMain);
		_panelLower = new NameTokenTagPanel(NameToken.factory(ClassToken, "LowerPanel"), new BorderLayout());
        _ctnContent.add(_panelLower, BorderLayout.SOUTH);
        _flagsStart = new FlagsInt();
        
        _barMenu = new JMenuBar();
        frameMain.setJMenuBar(_barMenu);
        
        // put in status bar
        _barStatus = new StatusBar(StatusBar.ClassToken);
        _panelLower.add(_barStatus, BorderLayout.SOUTH);
        
        _resCommonIcon = new ResourceImage(CommonMessageAppFrame.class, "/CDOAppIcon.png");
	}
	
	public FlagsIntTest getStartFlags() {
		return _flagsStart;
	}
	
	protected void setStartFlags(int... masks) {
		_flagsStart.set(masks);
	}
	
	protected void clearStartFlags(int... masks) {
		_flagsStart.clear(masks);
	}
    
    public void useCommonAppIcon() {
    	XResult result = _resCommonIcon.load();
    	if (result.isValid()) {
    		_ctnrFrame.setIconImage(_resCommonIcon.getImage());
    	}
    }
    
    public StatusBarTextItem addStatusBarTextItem(int nWidth) {
    	if (nWidth < 10) {
    		throw new IllegalArgumentException("allowable width is too small");
    	}
    	StatusBarTextItem itemStatus = new StatusBarTextItem(nWidth, "Ready");
        _barStatus.add(itemStatus);
        return itemStatus;
    }
    
    public ExitControl getExitControl() {
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
    
    protected void addToLowerPanel(JComponent component, Object constraints) {
    	_panelLower.add(component, constraints);
    }
    
    protected void completeSetup() {
    	// base class method does nothing   	
    }
    
    protected void buildMenus() {
    	// base class method does nothing
    }
    
    protected StatusBar getStatusBar() {
    	return _barStatus;
    }
    
    protected void repaintContent() {
    	_ctnContent.repaint();
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
    
    protected void doBeforeRendering() {
       	// base class method does nothing
    }
    
    protected void doOnceRunning() {
    	// base class method does nothing
    }

	@Override
	public void run() {
		_ctnrFrame.prepare();
		if (_flagsStart.test(START_MINSIZE_CURSIZE)) {
			_ctnrFrame.setMinimumSize(_ctnrFrame.getFrameSize());
		}
		doBeforeRendering();
		_ctnrFrame.makeVisible();
		doOnceRunning();
	}
	
	public static <A extends CommonAppFrame> void start(A app) {
		Objects.requireNonNull(app, "app");
		app.completeSetup();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}
}
