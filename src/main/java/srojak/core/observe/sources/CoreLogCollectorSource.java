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
package srojak.core.observe.sources;

import java.lang.reflect.InvocationTargetException;

import srojak.core.logic.FlagsInt;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCollectorSource;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public class CoreLogCollectorSource 
		implements ObservationCollectorSource, CoreObservers {
	private final FlagsInt _flags;
	private ObservationCollector _log;
	
	private static final PackageClassLocator LOC_KERNEL_LOG_SOURCE;
	
	private static final int FLAGS_VERBOSE = 0x1;
	
	static {
		LOC_KERNEL_LOG_SOURCE = new PackageClassLocator("srojak.kernel", "KernelLogCollectorSource");
	}
	
	public CoreLogCollectorSource() {
		_flags = new FlagsInt();
		_log = null;
	}
	
	public void setVerbose(boolean bState) {
		_flags.apply(bState, FLAGS_VERBOSE);
	}

	@Override
	public ObservationCollector getCollector() {
		// the srojak.kernel package may or may not be present
		
		if (_log == null) {
			try {
				Class<?> classSource = Class.forName(LOC_KERNEL_LOG_SOURCE.getFullName());
				try {
					ObservationCollectorSource source = (ObservationCollectorSource) classSource.getConstructor().newInstance();
					if (_flags.test(FLAGS_VERBOSE)) {
						OBS_OUT.recordClassOf(ObsLevel.DETAIL, "source class", source);
					}
					_log = source.getCollector();
				} catch (InstantiationException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				} catch (IllegalAccessException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				} catch (IllegalArgumentException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				} catch (InvocationTargetException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				} catch (NoSuchMethodException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				} catch (SecurityException e) {
					OBS_ERR.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_COLLECTOR, e, false);
					_log = OBS_ERR;
				}
			} catch (ClassNotFoundException e) {
				if (_flags.test(FLAGS_VERBOSE)) {
					OBS_OUT.record(ObsLevel.WARN, "did not find kernel class");
				}
				_log = OBS_ERR;
			}
			
		}
		
		return _log;
	}

}
