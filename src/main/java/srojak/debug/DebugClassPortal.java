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
package srojak.debug;

import java.util.Objects;

import srojak.core.mutable.BooleanMutable;
import srojak.core.reflect.ClassReflector;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.impl.ClassDebugOptionMap;
import srojak.debug.impl.DebugAccessorBase;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class DebugClassPortal 
		extends DebugAccessorBase 
		implements DebugNexusSwitchFlags {
	private final ClassReflector _reflMyClass;
	private final PackageClassLocator _locatorClass;

	public DebugClassPortal(Class<?> classCaller, int flags) {
		super(flags);
		Objects.requireNonNull(classCaller, "classCaller");
		_reflMyClass = new ClassReflector(classCaller);
		_locatorClass = new PackageClassLocator(_reflMyClass.getClass());
	}
	
	public DebugClassPortal(Object objCaller, int flags) {
		super(flags);
		Objects.requireNonNull(objCaller, "objCaller");
		_reflMyClass = new ClassReflector(objCaller);
		_locatorClass = new PackageClassLocator(_reflMyClass.getClass());
	}
	
	public DebugClassPortal(ClassReflector reflectorCaller, int flags) {
		super(flags);
		Objects.requireNonNull(reflectorCaller, "reflectorCaller");
		_reflMyClass = reflectorCaller;
		_locatorClass = new PackageClassLocator(_reflMyClass.getClass());
	}
	
	public DebugSwitch getMyClassSwitch() {
		DebugSwitchKey key = makeKeyForClass(_locatorClass);
		return fetchSwitch(key, new BooleanMutable(false));
	}
	
	public DebugSwitch getMyClassSubjectSwitch(String strSubject) {
		Objects.requireNonNull(strSubject, "strSubject");
		// TODO: who is responsible for validating subject strings?
		DebugSwitchKey key = makeKeyForClassSubject(_locatorClass, strSubject);
		return fetchSwitch(key, new BooleanMutable(false));
	}
	
	public void enableMyBaseClassSwitches() {
		DebugSwitchKey key = makeKeyForClass(_locatorClass);
		DebugNexusCore.enableBaseClassSwitches(key);
	}
	
	public ClassDebugOptions getMyClassOptions() {
		return fetchClassOptions(_locatorClass);
	}
	
	/**
	 * Set a debug option for this class.
	 * The portal must be created for modification.
	 * @param strName The name of the option.
	 * @param nValue The value for the option.
	 */
	public void setMyClassOption(String strName, int nValue) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		requireCanModify();
		ClassDebugOptionMap options = fetchClassOptions(_locatorClass);
		options.putOption(strName, nValue);
	}
}
