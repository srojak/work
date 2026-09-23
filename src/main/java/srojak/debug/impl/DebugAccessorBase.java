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

import java.nio.file.Path;
import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.mutable.BooleanMutable;
import srojak.core.observe.Announcer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.props.DebugProperties;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.props.PropertiesReadOnly;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.tools.BitMethods;
import srojak.debug.CommonDebugAccess;
import srojak.debug.DebugAccessConsFlags;
import srojak.debug.DebugNexusSwitchFlags;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchKeyBase;
import srojak.debug.DebugSwitchKeyClass;
import srojak.debug.DebugSwitchKeyClassSubject;

/**
 * @author Stephen
 *
 */
public abstract class DebugAccessorBase 
		implements CommonDebugAccess, DebugAccessConsFlags, DebugNexusSwitchFlags {
	private final int _flagsCons;
	
	protected static final BooleanMutable _commonFlag = new BooleanMutable(false);

	protected DebugAccessorBase(int flags) {
		_flagsCons = flags;
	}
	
	protected void requireCanModify() {
		if (!BitMethods.test(_flagsCons, CONS_CAN_MODIFY)) {
			throw new InvalidOperationException(getClass().getSimpleName(), "not open for modification");
		}
	}
	
	/**
	 * Get the current properties.
	 * @return The current properties collection.
	 */
	@Override
	public PropertiesReadOnly getProperties() {
		return DebugNexusCore.getProperties();
	}
	
	/**
	 * Get the writer for the debug switches.
	 * @return The current writer.
	 */
	@Override
	public ObservationWriter getWriter() {
		return DebugNexusCore.getWriter();
	}
	
	/**
	 * Set the writer for the debug switches.
	 * @param writer The writer to use.
	 */
	@Override
	public void setWriter(ObservationWriter writer) {
		requireCanModify();
		DebugNexusCore.setWriter(writer);
	}
	
	@Override
	public Announcer getAnnouncer() {
		return DebugNexusCore.getAnnouncer();
	}
	
	@Override
	public void setAnnouncer(Announcer announcer) {
		requireCanModify();
		DebugNexusCore.setAnnouncer(announcer);
	}
	
	@Override
	public ObsLevel getAnnounceLevel() {
		return DebugNexusCore.getAnnounceLevel();
	}
	
	@Override
	public void setAnnounceLevel(ObsLevel level) {
		requireCanModify();
		DebugNexusCore.setAnnounceLevel(level);
	}
	
	/**
	 * Get the log directory, if defined.
	 * @return A {@code Path} object identifying the log directory, or {@code null} if none is defined.
	 */
	@Override
	public Path getLogDirectory() {
		DebugProperties properties = DebugNexusCore.getProperties();
		String strPath = properties.getProperty(DebugPropertyKeys.LOG_DIR);
		if (strPath == null) {
			return null;
		} else {
			return Path.of(strPath);
		}
	}
	
	/**
	 * Make a key for a class.
	 * @param locator The locator identifying the package and class.
	 * @return The debug switch key for the class.
	 */
	public DebugSwitchKey makeKeyForClass(PackageClassLocator locator) {
		return new DebugSwitchKeyClass(locator);
	}
	
	/**
	 * Make a key for a class and subject.
	 * @param locator The locator identifying the package and class.
	 * @param strSubject The subject name.
	 * @return The debug switch key for the class and subject.
	 */
	public DebugSwitchKey makeKeyForClassSubject(PackageClassLocator locator, String strSubject) {
		return new DebugSwitchKeyClassSubject(locator, strSubject);
	}
	
	@Override
	public ObservationCollector getDebugObservationCollector() {
		return DebugNexusCore.DEBUG_OBSV;
	}
	
	protected DebugSwitchContent fetchSwitch(DebugSwitchKey key, BooleanMutable bIsNew) {
		bIsNew.setValue(false);
		DebugSwitchContent swDebug = DebugNexusCore.getContent(key);
		if (swDebug == null) {
			DebugSwitchKeyBase keyReal = (DebugSwitchKeyBase) key;
			swDebug = DebugNexusCore.createSwitch(keyReal);
			swDebug.setLevel(DebugNexusCore.getDefaultLogLevel());
			DebugNexusCore.putContent(swDebug);
			bIsNew.setValue(true);
		}
		return swDebug;
	}
	
	protected ClassDebugOptionMap fetchClassOptions(PackageClassLocator locClass) {
		ClassDebugOptionMap options = DebugNexusCore.getOptionsForClass(locClass);
		if (options == null) {
			options = DebugNexusCore.createOptionsForClass(locClass);
		}
		return options;
	}
	
	/**
	 * Get the debug level for a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The observation level defined by the debug switch.
	 */
	@Override
	public ObsLevel getDebugLevel(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		return fetchSwitch(key, _commonFlag).getLevel();
	}

	/**
	 * Get a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The debug switch.
	 */
	@Override
	public DebugSwitch getSwitch(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		return fetchSwitch(key, _commonFlag);
	}
}
