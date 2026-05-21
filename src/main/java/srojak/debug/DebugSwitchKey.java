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

import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * The interface for a key for a debug switch.
 */
public interface DebugSwitchKey {
	
	/**
	 * Get the class locator for the key.
	 * @return The class locator for the key.
	 */
	PackageClassLocator getClassLocator();
	
	/**
	 * Get the full name of the key.
	 * @return The String containing the full name of the key.
	 */
	String getFullName();
	
	/**
	 * Does the key have a subject name?
	 * @return {@code true} if the key has a subject name.
	 */
	boolean hasSubjectName();
	
	/**
	 * Get the subject name for the key, if any.
	 * @return The subject name, or {@code null} if the key does not have one.
	 */
	String getSubjectName();
	
	/**
	 * Get the hash code for the key.
	 * @return The hash code for the key.
	 */
	int hashCode();
}
