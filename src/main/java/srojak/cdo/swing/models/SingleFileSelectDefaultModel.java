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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import srojak.cdo.events.TextContentEvent;
import srojak.cdo.events.TextContentListener;
import srojak.cdo.events.TextReferents;
import srojak.cdo.swing.FileChooserSelectionMode;
import srojak.core.events.ActionStatusEvent;
import srojak.core.events.ActionStatusListener;
import srojak.core.events.StateChangeCodes;

/**
 * @author Stephen
 *
 */
public class SingleFileSelectDefaultModel 
		extends ControlModelBase 
		implements SingleFileSelectModel, StateChangeCodes, TextReferents {
	private final List<FileFilter> _listFilters;
	private FileChooserSelectionMode _modeSelect;
	private String _strFileName;
	private Path _pathStartDir;
	
	/**
	 * 
	 */
	public SingleFileSelectDefaultModel() {
		super();
		_listFilters = new LinkedList<FileFilter>();
		_modeSelect = FileChooserSelectionMode.FILES_ONLY;
		_strFileName = "";
		_pathStartDir = null;
	}

	@Override
	public Path getStartingDirectory() {
		return _pathStartDir;
	}

	@Override
	public void clearStartingDirectory() {
		_pathStartDir = null;
	}

	@Override
	public void setStartingDirectory(Path pathDir) {
		Objects.requireNonNull(pathDir, "pathDir");
		_pathStartDir = pathDir;
	}

	@Override
	public void addFileFilter(FileFilter filter) {
		Objects.requireNonNull(filter, "filter");
		_listFilters.add(filter);
	}

	@Override
	public FileChooserSelectionMode getFileSelectionMode() {
		return _modeSelect;
	}

	@Override
	public void setFileSelectionMode(FileChooserSelectionMode mode) {
		Objects.requireNonNull(mode, "mode");
		_modeSelect = mode;
	}

	@Override
	public void prepare(JFileChooser chooser) {
		Iterator<FileFilter> iterFilters = _listFilters.iterator();
		if (iterFilters.hasNext()) {
			FileFilter filter = iterFilters.next();
			chooser.setFileFilter(filter);
		}
		while (iterFilters.hasNext()) {
			FileFilter filter = iterFilters.next();
			chooser.addChoosableFileFilter(filter);
		}
		if (_pathStartDir != null) {
			chooser.setCurrentDirectory(_pathStartDir.toFile());
		}
		chooser.setFileSelectionMode(_modeSelect.getBaseMode());
	}

	@Override
	public boolean hasFileName() {
		return _strFileName != null && !_strFileName.isBlank();
	}

	@Override
	public String getFileName() {
		return _strFileName;
	}
	
	private void innerChangeFileName(String strName) {
		_strFileName = strName;
		TextContentEvent event = new TextContentEvent(this, TXR_MODEL, _strFileName == null ? "" : _strFileName);
		_listeners.forEach(TextContentListener.class, ls -> ls.textChanged(event));
	}

	@Override
	public void setFileName(String strName) {
		innerChangeFileName(strName);
		fireModelChanged(MA_CONTENT);
	}

	@Override
	public void statusChanged(ActionStatusEvent event) {
		ActionStatusEvent eventRelay = new ActionStatusEvent(this, event.getReferent(), event.getStatus());
		_listeners.forEach(ActionStatusListener.class, ls -> ls.statusChanged(eventRelay));
	}

	@Override
	public void textChanged(TextContentEvent event) {
		if (event.getReferent() == TXR_PRIMARY) {
			innerChangeFileName(event.getText());
		}
	}

	@Override
	public void addActionStatusListener(ActionStatusListener listener) {
		_listeners.add(ActionStatusListener.class, listener);
	}

	@Override
	public void removeActionStatusListener(ActionStatusListener listener) {
		_listeners.remove(ActionStatusListener.class, listener);
	}

	@Override
	public void addTextContentListener(TextContentListener listener) {
		_listeners.add(TextContentListener.class, listener);
	}

	@Override
	public void removeTextContentListener(TextContentListener listener) {
		_listeners.remove(TextContentListener.class, listener);
	}
}
