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

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.IPropertiesReadOnly;
import srojak.core.InvalidOperationException;
import srojak.core.logic.SimpleGate;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationWriter;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.result.XResult;
import srojak.core.tools.BitMethods;
import srojak.debug.impl.ClassDebugOptionMap;
import srojak.debug.impl.DebugNexusCore;
import srojak.debug.impl.DebugSwitchContent;
/**
 * @author Stephen
 *
 */
public class DebugNexus
		implements DebugNexusConsFlags, DebugNexusSwitchFlags {
	private final DebugProperties _properties;
	private final int _flags;
	
	private static final SimpleGate commonGate = new SimpleGate();
	private static final String className = DebugNexus.class.getSimpleName();
	
	/**
	 * Default constructor.
	 */
	public DebugNexus() {
		_properties = DebugNexusCore.getProperties();
		_flags = CONS_NONE;
	}
	
	public DebugNexus(int flags) {
		_properties = DebugNexusCore.getProperties();
		_flags = flags;
	}
	
	/**
	 * Get the auto-flush setting.
	 * @return The current auto-flush setting.
	 */
	public boolean getAutoFlush() {
		return DebugNexusCore.getAutoFlush();
	}
	
	/**
	 * Set the auto-flush setting.
	 * @param bState The desired state of the setting.
	 */
	public void setAutoFlush(boolean bState) {
		DebugNexusCore.setAutoFlush(bState);
	}
	
	/**
	 * Load properties from a file in the current directory.
	 * 
	 */
	public XResult loadPropertiesFromCurrentDir() {
		return _properties.loadFromCurrentDirectory(DebugNexusCore.PROPERTIES_FILE_NAME);
	}
	
	/**
	 * Get the current properties.
	 * @return The current properties collection.
	 */
	public IPropertiesReadOnly getProperties() {
		return _properties;
	}
	
	/**
	 * Get the writer for the debug switches.
	 * @return The current writer.
	 */
	public ObservationWriter getWriter() {
		return DebugNexusCore.getWriter();
	}
	
	/**
	 * Set the writer for the debug switches.
	 * @param writer The writer to use.
	 */
	public void setWriter(ObservationWriter writer) {
		DebugNexusCore.setWriter(writer);
	}
	
	/**
	 * Get the log directory, if defined.
	 * @return A {@code Path} object identifying the log directory, or {@code null} if none is defined.
	 */
	public Path getLogDirectory() {
		String strPath = _properties.getProperty(DebugPropertyKeys.LOG_DIR);
		if (strPath == null) {
			return null;
		} else {
			return Path.of(strPath);
		}
	}
	
	private DebugSwitchContent fetchSwitch(DebugSwitchKey key, SimpleGate gateNew) {
		gateNew.setGateState(false);
		DebugSwitchContent swDebug = DebugNexusCore.getContent(key);
		if (swDebug == null) {
			DebugSwitchKeyBase keyReal = (DebugSwitchKeyBase) key;
			swDebug = new DebugSwitchContent(keyReal);
			swDebug.setLevel(DebugNexusCore.getDefaultLogLevel());
			DebugNexusCore.putContent(swDebug);
			gateNew.setGateState(true);
		}
		return swDebug;
	}
	
	/**
	 * Get the debug level for a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The observation level defined by the debug switch.
	 */
	public ObsLevel getDebugLevel(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		return fetchSwitch(key, commonGate).getLevel();
	}
	
	/**
	 * Get a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The debug switch.
	 */
	public DebugSwitch getSwitch(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		return fetchSwitch(key, commonGate);
	}
	
	/**
	 * Get a specific debug switch.
	 * Code can use this to set options only if they are not already set earlier.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @param levelNew The level to set the switch to, if created.
	 * @param bShowSource The state of the show source option to set the switch to, if created.
	 * @return The debug switch.
	 */
	public DebugSwitch getSwitch(DebugSwitchKey key,
			ObsLevel levelNew, boolean bShowSource) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(levelNew, "levelNew");
		SimpleGate gate = new SimpleGate();
		DebugSwitchContent swDebug = fetchSwitch(key, gate);
		if (gate.getGateState()) {
			swDebug.setLevel(levelNew);
			swDebug.setShowSourceLocations(bShowSource);
		}
		return swDebug;
	}
	
	public void enableBaseClassSwitches(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		DebugNexusCore.enableBaseClassSwitches(key);
	}
	
	/**
	 * Set the debug level for a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * The nexus must be created for modification.
	 * @param key The key identifying the debug switch.
	 * @param level The observation level to assign.
	 * @param bShowSource If {@code true}, the switch should show source information in its output.
	 */
	public void setDebugLevel(DebugSwitchKey key, ObsLevel level, boolean bShowSource) {
		Objects.requireNonNull(key, "key");
		if (!BitMethods.test(_flags, CONS_CAN_MODIFY)) {
			throw new InvalidOperationException(className, "not open for modification");
		}
		DebugSwitchContent swDebug = fetchSwitch(key, commonGate);
		swDebug.setLevel(level);
		swDebug.setShowSourceLocations(bShowSource);
	}
	
	/**
	 * Iterate over all the defined switches.
	 * @param consumer The consumer to receive each switch.
	 */
	public void forEachSwitch(Consumer<DebugSwitch> consumer) {
		DebugSwitch[] switches = DebugNexusCore.getAllSwitches();
		for (DebugSwitch ds : switches) {
			consumer.accept(ds);
		}
	}
	
	/**
	 * Iterate over all the defined switches in sorted order.
	 * @param consumer The consumer to receive each switch.
	 */
	public void forEachSwitchSorted(Consumer<DebugSwitch> consumer) {
		DebugNexusCore.getAllSwitchKeysAsStream()
				.sorted().forEach(k -> {
					DebugSwitch sw = DebugNexusCore.getContent(k);
					consumer.accept(sw);
				});
	}
	
	private ClassDebugOptionMap fetchClassOptions(PackageClassLocator locClass) {
		ClassDebugOptionMap options = DebugNexusCore.getOptionsForClass(locClass);
		if (options == null) {
			options = DebugNexusCore.createOptionsForClass(locClass);
		}
		return options;
	}
	
	/**
	 * Get the debug options for a class.
	 * @param locClass The locator for the class for which to find options.
	 * @return The defined debug options; an empty set will be created if not already defined.
	 */
	public ClassDebugOptions getClassOptions(PackageClassLocator locClass) {
		Objects.requireNonNull(locClass, "locClass");
		return fetchClassOptions(locClass);
	}
	
	/**
	 * Get the debug options for a class.
	 * @param classOwner The class for which to find options.
	 * @return The defined debug options; an empty set will be created if not already defined.
	 */
	public ClassDebugOptions getClassOptions(Class<?> classOwner) {
		Objects.requireNonNull(classOwner, "classOwner");
		PackageClassLocator locator = new PackageClassLocator(classOwner);
		return fetchClassOptions(locator);
	}
	
	/**
	 * Set a debug option for a class.
	 * The nexus must be created for modification.
	 * @param classOwner The class for which to set the option.
	 * @param strName The name of the option.
	 * @param nValue The value for the option.
	 */
	public void setClassOption(Class<?> classOwner, String strName, int nValue) {
		Objects.requireNonNull(classOwner, "classOwner");
		if (!BitMethods.test(_flags, CONS_CAN_MODIFY)) {
			throw new InvalidOperationException(className, "not open for modification");
		}
		PackageClassLocator locator = new PackageClassLocator(classOwner);
		ClassDebugOptionMap options = fetchClassOptions(locator);
		options.putOption(strName, nValue);
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
	
	/**
	 * Create a pass-through list.
	 * @param strings The strings to enter into the list.
	 * @return The pass-through list.
	 * @see ObsPassThroughList for use of the pass-through list.
	 */
	public static ObsPassThroughList makePassThroughList(String[] strings) {
		Objects.requireNonNull(strings);
		return ObsPassThroughList.createFrom(strings);
	}
}
