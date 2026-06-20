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
package srojak.cdo.swing;

import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Stephen
 *
 */
public class AppFrameContainer {
	private final JFrame _frame;
	private Image _imageIcon;
	
	public AppFrameContainer(JFrame frame) {
		Objects.requireNonNull(frame, "frame");
		_frame = frame;
		_imageIcon = _frame.getIconImage();
	}
	
	public void setIconImage(Image image) {
		_imageIcon = image;
		_frame.setIconImage(_imageIcon);
	}
	
	public Image getIconImage() {
		return _imageIcon;
	}
	
	public void makeVisible() {
		_frame.pack();
		_frame.setVisible(true);		
	}
    
    public int showOpenFileDialog(JFileChooser chooser) {
    	return chooser.showOpenDialog(_frame);
    }
    
    public int showSaveFileDialog(JFileChooser chooser) {
    	return chooser.showSaveDialog(_frame);
    }
    
   	public <T extends Window> T createWithParentFrame(Function<JFrame, T> function) {
   		return function.apply(_frame);
   	}
   	
   	public void showMessageDialog(Object objMessage, String strTitle, int typeMessage) {
   		JOptionPane.showMessageDialog(_frame, objMessage, strTitle, typeMessage);
   	}
   	
   	public int showConfirmDialog(Object objMessage, String strTitle, int typeOption) {
   		return JOptionPane.showConfirmDialog(_frame, objMessage, strTitle, typeOption);
   	}
   	
   	public int showConfirmDialog(Object objMessage, String strTitle, int typeOption, int typeMessage) {
   		return JOptionPane.showConfirmDialog(_frame, objMessage, strTitle, typeOption, typeMessage);
   	}
   	
   	public String showInputDialog(Object objMessage, String strTitle, int typeMessage) {
   		return JOptionPane.showInputDialog(_frame, objMessage, strTitle, typeMessage);
   	}
    
    protected FrameWindowListenerTool getFrameWindowListenerTool() {
    	return new FrameWindowListenerTool();
    }
	
	public class FrameWindowListenerTool {
		
		private FrameWindowListenerTool() {
			
		}
		
		public void addWindowListener(WindowListener listener) {
			_frame.addWindowListener(listener);
		}
		
		public void removeWindowListener(WindowListener listener) {
			_frame.removeWindowListener(listener);
		}
	}
}
