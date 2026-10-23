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

import java.util.Objects;

import srojak.core.observe.CoreObservers;
import srojak.core.observe.HasReplaceableObservationCollector;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;

/**
 * @author Stephen
 *
 */
public class VisibleClassLoader 
		extends ClassLoader 
		implements HasReplaceableObservationCollector {
	private ObservationCollector _obsCollect;

	/**
	 * 
	 */
	public VisibleClassLoader() {
		super();
		_obsCollect = CoreObservers.OBS_LOG;
	}

	/**
	 * @param parent
	 */
	public VisibleClassLoader(ClassLoader parent) {
		super(parent);
		_obsCollect = CoreObservers.OBS_LOG;
	}

	/**
	 * @param name
	 * @param parent
	 */
	public VisibleClassLoader(String name, ClassLoader parent) {
		super(name, parent);
		_obsCollect = CoreObservers.OBS_LOG;
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _obsCollect;
	}

	@Override
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_obsCollect = collector;
	}

	@Override
	protected Class<?> findClass(String name) 
			throws ClassNotFoundException {
		_obsCollect.record(ObsLevel.INFO, "findClass called for \"" + name + "\"");
		Class<?> classValue = super.findClass(name);
		if (classValue == null) {
			_obsCollect.record(ObsLevel.INFO, "findClass returning (null)");
		} else {
			_obsCollect.record(ObsLevel.INFO, "found class " + classValue.getName());
		}
		return classValue;
	}

	@Override
	protected Class<?> findClass(String moduleName, String name) {
		_obsCollect.record(ObsLevel.INFO, "findClass called for module " + moduleName + "and \"" + name + "\"");
		Class<?> classValue =  super.findClass(moduleName, name);
		if (classValue == null) {
			_obsCollect.record(ObsLevel.INFO, "findClass returning (null)");
		} else {
			_obsCollect.record(ObsLevel.INFO, "found class " + classValue.getName());
		}
		return classValue;
	}
}
