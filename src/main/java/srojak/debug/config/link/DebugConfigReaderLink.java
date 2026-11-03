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
package srojak.debug.config.link;

import java.nio.file.Path;

import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.debug.DebugConfigFileReader;
import srojak.debug.config.DebugConfigParser2;
import srojak.debug.config.DebugConfigReaderImpl;
import srojak.debug.config.impl.DebugAccess;
import srojak.debug.control.DebugControl;

/**
 * @author Stephen
 *
 */
public class DebugConfigReaderLink 
		implements DebugConfigFileReader {
	private final DebugConfigReaderImpl _reader;
	
	public DebugConfigReaderLink() {
		Module moduleDebug = DebugControl.class.getModule();
		Module moduleSelf = getClass().getModule();
		moduleSelf.addExports("srojak.debug.config.impl", moduleDebug);
		moduleSelf.addOpens("srojak.debug.config.impl", moduleDebug);

		DebugAccess.start();
		@SuppressWarnings("unused")
		DebugControl controller = DebugAccess.getDebugController(true);
		DebugConfigParser2 parser = new DebugConfigParser2();
		_reader = new DebugConfigReaderImpl(parser);
	}

	@Override
	public void setClassWriterObsLevel(ObsLevel level) {
		_reader.setClassWriterObsLevel(level);
	}

	@Override
	public void setParserWriterObsLevel(ObsLevel level) {
		_reader.setParserWriterObsLevel(level);
	}

	@Override
	public XResult readConfigFile(Path pathFile, FileExistence exists) {
		return _reader.readConfigFile(pathFile, exists);
	}

	@Override
	public XResult readConfigFile(String strFile, FileExistence exists) {
		return _reader.readConfigFile(strFile, exists);
	}

}
