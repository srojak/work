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
package srojak.debug.control;

import srojak.core.io.IONodeIdentifier;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugSwitchKey;

/**
 * @author Stephen
 *
 */
public interface DebugControl {

	void startReadingConfig(IONodeIdentifier ident);
	void endReadingConfig(IONodeIdentifier ident);
	void readingSwitchControlSet(String strName);
	DebugSwitchMutable getDebugSwitch(DebugSwitchKey key);
	DebugSwitchMutable createDebugSwitch(DebugSwitchKey key);
	ClassDebugOptionsMutable getOrCreateClassOptions(PackageClassLocator locClass);
	void enableBaseClassSwitches(DebugSwitchKey key);
}
