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

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import srojak.cdo.swing.FileChooserAction;
import srojak.cdo.swing.FileChooserSelectionMode;
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
	private FileExistence _fexOpen;
	private FileExistence _fexSave;
	private FileExistence _fexCustom;
	private FileExistence _fexActive;
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
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectoryPath
	 */
	public FileChooserConfirming(String currentDirectoryPath) {
		super(currentDirectoryPath);
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectory
	 */
	public FileChooserConfirming(File currentDirectory) {
		super(currentDirectory);
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param fsv
	 */
	public FileChooserConfirming(FileSystemView fsv) {
		super(fsv);
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectory
	 * @param fsv
	 */
	public FileChooserConfirming(File currentDirectory, FileSystemView fsv) {
		super(currentDirectory, fsv);
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}

	/**
	 * @param currentDirectoryPath
	 * @param fsv
	 */
	public FileChooserConfirming(String currentDirectoryPath, FileSystemView fsv) {
		super(currentDirectoryPath, fsv);
		_fexOpen = FileExistence.MustExist;
		_fexSave = FileExistence.ConfirmIfExists;
		_fexCustom = FileExistence.Any;
		_fexActive = FileExistence.Any;
		_bInferExt = true;
	}
	
	public void setFileSelectionMode(FileChooserSelectionMode mode) {
		Objects.requireNonNull(mode, "mode");
		super.setFileSelectionMode(mode.getBaseMode());
	}
	
	@Override
	public int showOpenDialog(Component parent) 
			throws HeadlessException {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "OpenDialog");
		_fexActive = _fexOpen;
		int nReturn = super.showOpenDialog(parent);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "returning " + nReturn);
		return nReturn;
	}

	@Override
	public int showSaveDialog(Component parent) 
			throws HeadlessException {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "SaveDialog");
		_fexActive = _fexSave;
		int nReturn = super.showSaveDialog(parent);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "returning " + nReturn);
		return nReturn;
	}

	@Override
	public int showDialog(Component parent, String approveButtonText) 
			throws HeadlessException {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "CustomDialog");
		_fexActive = _fexCustom;
		int nReturn = super.showDialog(parent, approveButtonText);
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH, () -> "returning " + nReturn);
		return nReturn;
	}

	public FileExistence getFileExistenceBehavior(FileChooserAction act) {
		Objects.requireNonNull(act, "act");
		switch (act) {
		case Open:
			return _fexOpen;
			
		case Save:
			return _fexSave;
			
		case Custom:
			break;
		}
		return _fexCustom;
	}
	
	public void setFileExistenceBehavior(FileChooserAction act, FileExistence fex) {
		Objects.requireNonNull(act, "act");
		Objects.requireNonNull(fex, "fex");
		switch (act) {
		case Open:
			_fexOpen = fex;
			break;
			
		case Save:
			_fexSave = fex;
			break;
			
		case Custom:
			_fexCustom = fex;
			break;
		}
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
		switch (_fexActive) {
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
		
		if (_bInferExt && !fileSelected.isDirectory()) {
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
