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

import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * Common methos for making keys.
 */
public class DebugSwitchTool {
	
	public static DebugSwitchKey makeClassKey(Object objInstance) {
		Objects.requireNonNull(objInstance, "objInstance");
		return new DebugSwitchKeyClass(objInstance.getClass());
	}
	
	public static DebugSwitchKey makeClassKey(Class<?> classOwner) {
		Objects.requireNonNull(classOwner, "classOwner");
		return new DebugSwitchKeyClass(classOwner);
	}
	
	public static DebugSwitchKey makeClassKey(PackageClassLocator locator) {
		Objects.requireNonNull(locator, "locator");
		return new DebugSwitchKeyClass(locator);
	}
	
	public static DebugSwitchKey makeClassKey(String strPackage, String strClass) {
		Objects.requireNonNull(strPackage, "strPackage");
		Objects.requireNonNull(strClass, "strClass");
		return new DebugSwitchKeyClass(new PackageClassLocator(strPackage, strClass));
	}
	
	public static DebugSwitchKey makeClassSubjectKey(Class<?> classOwner, String strSubject) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strSubject, "strSubject");
		return new DebugSwitchKeyClassSubject(classOwner, strSubject);
	}
	
	public static DebugSwitchKey makeClassSubjectKey(PackageClassLocator locator, String strSubject) {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(strSubject, "strSubject");
		return new DebugSwitchKeyClassSubject(locator, strSubject);
	}
	
	public static DebugSwitchKey makeClassSubjectKey(String strPackage, String strClass,
			String strSubject) {
		Objects.requireNonNull(strPackage, "strPackage");
		Objects.requireNonNull(strClass, "strClass");
		Objects.requireNonNull(strSubject, "strSubject");
		return new DebugSwitchKeyClassSubject(new PackageClassLocator(strPackage, strClass), strSubject);		
	}
}
