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

import java.io.File;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class FileChooserConfirming
		extends JFileChooser {
	private FileExistence _fexists;
	private boolean _bInferExt;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = FileChooserConfirming.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * 
	 */
	public FileChooserConfirming() {
		super();
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectoryPath
	 */
	public FileChooserConfirming(String currentDirectoryPath) {
		super(currentDirectoryPath);
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectory
	 */
	public FileChooserConfirming(File currentDirectory) {
		super(currentDirectory);
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param fsv
	 */
	public FileChooserConfirming(FileSystemView fsv) {
		super(fsv);
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectory
	 * @param fsv
	 */
	public FileChooserConfirming(File currentDirectory, FileSystemView fsv) {
		super(currentDirectory, fsv);
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectoryPath
	 * @param fsv
	 */
	public FileChooserConfirming(String currentDirectoryPath, FileSystemView fsv) {
		super(currentDirectoryPath, fsv);
		_fexists = FileExistence.Any;
		_bInferExt = true;
	}
	
	public FileExistence getFileExistenceBehavior() {
		return _fexists;
	}
	
	public void setFileExistenceBehavior(FileExistence fex) {
		Objects.requireNonNull(fex, "fex");
		_fexists = fex;
	}
	
	public boolean getInferExtension() {
		return _bInferExt;
	}
	
	public void setInferExtension(boolean bState) {
		_bInferExt = bState;
	}
	
	private boolean fileMustExist(File fileSelected) {
		if (fileSelected != null && !fileSelected.exists()) {
			JOptionPane.showMessageDialog(this, "Choose a file that already exists",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean fileMustNotExist(File fileSelected) {
		if (fileSelected != null && fileSelected.exists()) {
			JOptionPane.showMessageDialog(this, "Enter a name of a file that does not already exist",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean confirmWhenFileExists(File fileSelected) {
		if (fileSelected != null && fileSelected.exists()) {
			int nResponse = JOptionPane.showConfirmDialog(this, 
					"The file exists; do you want to replace it?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (nResponse == JOptionPane.NO_OPTION) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void approveSelection() {
		File fileSelected = getSelectedFile();
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "path = \""
				+ fileSelected.getAbsolutePath() + "\"");
		switch (_fexists) {
		case Any:
			break;
			
		case MustExist:
			if (!fileMustExist(fileSelected)) {
				return;
			}
			break;
			
		case MustNotExist:
			if (!fileMustNotExist(fileSelected)) {
				return;
			}
			break;
			
		case ConfirmIfExists:
			if (!confirmWhenFileExists(fileSelected)) {
				return;
			}
			break;
		}
		
		if (_bInferExt) {
			_swDebugClass.write(ObsLevel.DEBUG, "will attempt to infer extension");
			FileFilter filterCurrent = getFileFilter();
			String strExtension = null;
			if (filterCurrent instanceof FileNameExtensionFilter filterExt) {
				String[] strExts = filterExt.getExtensions();
				if (strExts.length > 0) {
					strExtension = strExts[0];
				}
			}
			if (strExtension != null) {
				_swDebugClass.write(ObsLevel.DEBUG, "inferred extension is ." + strExtension);
				String strFileBaseName = fileSelected.getName().toLowerCase();
				int index = strFileBaseName.lastIndexOf('.');
				if (index < 0) {
					String strFileName = fileSelected.getAbsolutePath();
					File fileReplace = new File(strFileName + "." + strExtension);
					setSelectedFile(fileReplace);
					_swDebugClass.write(ObsLevel.DEBUG, () -> "changed file name to \""
						+ fileReplace.getName() + "\"");
				}
			}
		}
		
		super.approveSelection();
	}
}
