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
package srojak.debug.impl;

import srojak.core.io.IONodeIdentifier;
import srojak.core.kernel.Kernel;
import srojak.core.kernel.KernelLog;
import srojak.core.observe.ObservationWriter;
import srojak.core.props.DebugProperties;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugSwitchKey;
import srojak.debug.control.ClassDebugOptionsMutable;
import srojak.debug.control.DebugControl;
import srojak.debug.control.DebugSwitchMutable;

/**
 * @author Stephen
 *
 */
public class DebugControlInstance 
		implements DebugControl {
	private final DebugProperties _properties;
	private final ObservationWriter _writerLog;
	
	public DebugControlInstance() {
		_properties = Kernel.getDebugProperties();
		_writerLog = KernelLog.getLogWriter();
	}

	@Override
	public void startReadingConfig(IONodeIdentifier ident) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writerLog.writeDiagnostic("starting " + ident);
		}
	}

	@Override
	public void endReadingConfig(IONodeIdentifier ident) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writerLog.writeDiagnostic("completed " + ident);
		}
		DebugNexusCore.closeControlSet();
	}

	@Override
	public void readingSwitchControlSet(String strName) {
		DebugNexusCore.readingSwitchControlSet(strName);
	}

	@Override
	public DebugSwitchMutable getDebugSwitch(DebugSwitchKey key) {
		return DebugNexusCore.getContent(key);
	}

	@Override
	public DebugSwitchMutable createDebugSwitch(DebugSwitchKey key) {
		DebugSwitchContent content = DebugNexusCore.createSwitch(key);
		DebugNexusCore.putContent(content);
		return content;
	}

	@Override
	public ClassDebugOptionsMutable getOrCreateClassOptions(PackageClassLocator locClass) {
		ClassDebugOptionMap options = DebugNexusCore.getOptionsForClass(locClass);
		if (options == null) {
			options = DebugNexusCore.createOptionsForClass(locClass);
		}
		return options;
	}

	@Override
	public void enableBaseClassSwitches(DebugSwitchKey key) {
		DebugNexusCore.enableBaseClassSwitches(key);
	}

}
