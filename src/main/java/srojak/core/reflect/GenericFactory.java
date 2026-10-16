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
package srojak.core.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.sources.ObservationCollectorCommonErrorSource;

/**
 * @author Stephen
 *
 */
public class GenericFactory {
	
	private static ObservationCollector _collector;
	
	static {
		ObservationCollectorCommonErrorSource source = new ObservationCollectorCommonErrorSource();
		_collector = source.getCollector();
	}

	public static <T> T createInstanceRaw(ClassConsReflector<T> reflectorTarget) 
			throws NoSuchMethodException, SecurityException, InstantiationException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Objects.requireNonNull(reflectorTarget, "reflectorTarget");
		_collector.record(ObsLevel.TRACE, "called generic factory for class " + reflectorTarget.getName());
		// do it raw first
		Class<T> classTarget = reflectorTarget.getReflectedClassAs();
		Constructor<T> constructor = classTarget.getConstructor();
		return constructor.newInstance();
	}
	
	public static <T> T createInstanceRaw(Class<T> classTarget) 
			throws NoSuchMethodException, SecurityException, InstantiationException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Objects.requireNonNull(classTarget, "classTarget");
		_collector.record(ObsLevel.TRACE, "called generic factory for class " + classTarget.getName());
		Constructor<T> constructor = classTarget.getConstructor();
		return constructor.newInstance();
	}
}
