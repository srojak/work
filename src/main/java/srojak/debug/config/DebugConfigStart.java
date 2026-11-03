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
package srojak.debug.config;

import srojak.core.field.SetOnce;
import srojak.core.io.FileExistence;
import srojak.core.main.CoreCollectors;
import srojak.core.main.CoreOperations;
import srojak.core.observe.ObsLevel;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.debug.DebugConfigFileReader;

/**
 * @author Stephen
 *
 */
public class DebugConfigStart
		extends CoreOperations {
	private final SetOnce<DebugConfigFileReader> _reader;

	/**
	 * @param reflectApp
	 */
	public DebugConfigStart(ClassReflector reflectApp) {
		super(reflectApp);
		_reader = new SetOnce<DebugConfigFileReader>(SetOnce.DEFAULT);
	}

	/**
	 * @param classApp
	 */
	public DebugConfigStart(Class<?> classApp) {
		super(classApp);
		_reader = new SetOnce<DebugConfigFileReader>(SetOnce.DEFAULT);
	}

	/**
	 * @param app
	 */
	public DebugConfigStart(Object app) {
		super(app);
		_reader = new SetOnce<DebugConfigFileReader>(SetOnce.DEFAULT);
	}

	@Override
	public void initialize() {
		super.initialize();
		_reader.set(new DebugConfigReaderImpl());
	}

	public XResult readDebugConfig() {
		DebugConfigReaderImpl reader = (DebugConfigReaderImpl) _reader.get();
		CoreCollectors outputs = new CoreCollectors();
		XResult resultSchema = reader.loadSchema();
		if (!resultSchema.isValid()) {
			outputs.log().recordThrowable(ObsLevel.ALERT, resultSchema.getActivity(), resultSchema.getException(), false);
			return resultSchema;
		}
		
		reader.setRecordComments(true);
		XResult resultRead = reader.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		outputs.log().recordResult(ObsLevel.INFO, ObsLevel.ERROR, resultRead, false);
		if (!resultRead.isValid()) {
			outputs.log().recordThrowable(ObsLevel.FATAL, resultRead.getActivity(), resultRead.getException(), false);
		}
		
		return resultRead;
	}
}
