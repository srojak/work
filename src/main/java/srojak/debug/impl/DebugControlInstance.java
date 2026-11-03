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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.core.io.IONodeIdentifier;
import srojak.core.main.CoreCentral;
import srojak.core.main.CoreCollectors;
import srojak.core.observe.ObsLevelInstancePolicyHolder;
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
	private final Map<String, ObsLevelInstancePolicyHolder> _mapPolicies;
	private final ObservationWriter _writerLog;
	
	public DebugControlInstance() {
		_properties = CoreCentral.getDebugProperties();
		_mapPolicies = new HashMap<String, ObsLevelInstancePolicyHolder>();
		CoreCollectors outputs = new CoreCollectors();
		// TODO: should be using the collector
		_writerLog = outputs.getLogWriter();
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

	@Override
	public boolean containsInstancePolicy(String strName) {
		return _mapPolicies.containsKey(strName);
	}

	@Override
	public void defineInstancePolicy(String strName, ObsLevelInstancePolicyHolder policy) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		Objects.requireNonNull(policy, "policy");
		_mapPolicies.put(strName, policy);
	}

	@Override
	public ObsLevelInstancePolicyHolder getInstancePolicy(String strName) {
		return _mapPolicies.get(strName);
	}

}
