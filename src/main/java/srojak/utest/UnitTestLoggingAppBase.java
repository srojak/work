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
package srojak.utest;

import srojak.core.field.SetOnce;
import srojak.core.io.LogFileLocator;
import srojak.core.main.CoreCollectors;
import srojak.core.main.CoreOperations;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObservationCollectorOverlay;
import srojak.core.observe.ObservationCommonCollector;

/**
 * @author Stephen
 *
 */
public class UnitTestLoggingAppBase
		implements CoreObservers {
	private final SetOnce<CoreOperations> _operations;
	
	public UnitTestLoggingAppBase() {
		_operations = new SetOnce<CoreOperations>(SetOnce.DEFAULT);
	}
	
	public void initializeCore() {
		CoreOperations ops = new CoreOperations(this);
		_operations.set(ops);
		ops.initialize();
	}
	
	public boolean tryOpenLogFile(ObservationCommonCollector collectObs, String strPrefix) {
		CoreOperations ops = _operations.get();
		
		LogFileLocator locLog = ops.locateImplicitLogFile(strPrefix);
		return ops.tryOpenLogFile(locLog);
	}
}
