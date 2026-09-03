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
package srojak.cdo.swing.models;

import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import srojak.cdo.CanBeEnabled;
import srojak.cdo.events.ModelChangeOriginator;
import srojak.cdo.events.TextContentListener;
import srojak.cdo.events.TextContentOriginator;
import srojak.cdo.swing.CDOControlModel;
import srojak.cdo.swing.FileChooserSelectionMode;
import srojak.core.events.ActionStatusListener;
import srojak.core.events.ActionStatusOriginator;

/**
 * @author Stephen
 *
 */
public interface SingleFileSelectModel 
		extends CDOControlModel, CanBeEnabled, ActionStatusOriginator, ModelChangeOriginator,
			TextContentListener, TextContentOriginator, ActionStatusListener {
	
	Path getStartingDirectory();
	void clearStartingDirectory();
	void setStartingDirectory(Path pathDir);
	void addFileFilter(FileFilter filter);
	FileChooserSelectionMode getFileSelectionMode();
	void setFileSelectionMode(FileChooserSelectionMode mode);
	void prepare(JFileChooser chooser);
	boolean hasFileName();
	String getFileName();
	void setFileName(String strName);
}
