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
package srojak.kernel;

import java.nio.file.Path;

import srojak.core.logic.OnceFlag;
import srojak.core.main.CoreCentral;
import srojak.core.main.CoreOutputs;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservedActivity;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultModifierFlags;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public abstract class SelfLoaderBase
		implements Loader {
	private final OnceFlag _fLoaded;
	protected final ObservationCollector _obsKernel;
	
	protected SelfLoaderBase() {
		_fLoaded = new OnceFlag();
		CoreOutputs outputs = new CoreOutputs();
		_obsKernel = outputs.log();
	}
		
	@Override
	public final boolean isLoaded() {
		return _fLoaded.isSet();
	}
	
	protected abstract XResult doLoad();

	@Override
	public XResult load() {
		if (!_fLoaded.isSet()) {
			XResult result = doLoad();
			_fLoaded.set();
			return result;
		} else {
			XResultStatusCarrier result = new XResultStatusCarrier(ObservedActivity.INIT);
			result.setValid();
			result.setModifierFlag(XResultModifierFlags.MOD_DONE_ONCE);
			return result;
		}
	}
	
	protected Path getAppDirPath() {
		return CoreCentral.getAppDirPath();
	}
	
	protected ClassReflector getAppClass() {
		return CoreCentral.getAppClass();
	}
}
