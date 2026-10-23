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

import java.util.Objects;

import srojak.core.io.LogFileLocator;
import srojak.core.main.CoreCollectors;
import srojak.core.main.CoreOperations;
import srojak.core.observe.ObservationCollectorOverlay;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public final class UnitTestCommon {
	
	public static final ObservationCollectorOverlay UTEST_OUT;
	public static final ObservationCollectorOverlay UTEST_ERR;
	
	static {
		CoreCollectors outputs = new CoreCollectors();
		
		UTEST_OUT = ObservationCollectorOverlay.makeInstance();
		UTEST_OUT.addCollector(outputs.log());
		UTEST_OUT.addCollector(outputs.output());
		
		UTEST_ERR = ObservationCollectorOverlay.makeInstance();
		UTEST_ERR.addCollector(outputs.log());
		UTEST_ERR.addCollector(outputs.error());
	}
	
	public static boolean startAndOpenLogFile(Class<?> classApp, String strPrefix) {
		Objects.requireNonNull(classApp, "classApp");
		Objects.requireNonNull(strPrefix, "strPrefix");
		CoreOperations operations = new CoreOperations(classApp);
		operations.initialize();
		
		LogFileLocator locLog = operations.locateImplicitLogFile(strPrefix);
		return operations.tryOpenLogFile(locLog);
	}
	
	public static boolean startAndOpenLogFile(ClassReflector reflClass, String strPrefix) {
		Objects.requireNonNull(reflClass, "reflClass");
		return startAndOpenLogFile(reflClass.getReferencedClass(), strPrefix);
	}
}
