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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.mutable.BooleanMutable;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.reflect.ClassReflector;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.impl.DebugAccessorBase;
import srojak.debug.impl.DebugNexusCore;
import srojak.debug.impl.DebugSwitchContent;
/**
 * @author Stephen
 *
 */
public class DebugNexus
		extends DebugAccessorBase
		implements DebugAccessConsFlags, DebugNexusSwitchFlags {
	
	/**
	 * Default constructor.
	 */
	public DebugNexus() {
		super(CONS_NONE);
	}
	
	public DebugNexus(int flags) {
		super(flags);
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
		BooleanMutable bIsNew = new BooleanMutable(false);
		DebugSwitchContent swDebug = fetchSwitch(key, bIsNew);
		if (bIsNew.getValue()) {
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
		requireCanModify();
		DebugSwitchContent swDebug = fetchSwitch(key, _commonFlag);
		swDebug.setLevel(level);
		swDebug.setShowSourceLocations(bShowSource);
	}
	
	/**
	 * Iterate over all the defined switches.
	 * @param consumer The consumer to receive each switch.
	 */
	public void forEachSwitch(Consumer<DebugSwitch> consumer) {
		DebugNexusCore.getAllSwitches().forEach(ds -> consumer.accept(ds));
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
	
	/**
	 * Get a sorted list of keys for all of the debug switches.
	 * @return A list of all keys.
	 */
	public List<DebugSwitchKey> getSortedSwitchKeys() {
		return DebugNexusCore.getAllSwitchKeysAsStream().sorted().toList();
	}
	
	/**
	 * Get a sorted list of keys for all of the class options.
	 * @return A list of all keys.
	 */
	public List<PackageClassLocator> getSortedClassOptionKeys() {
		return DebugNexusCore.getAllClassOptionKeysAsStream().sorted().toList();
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
	 * Get the debug options for a class.
	 * @param reflector The reflector for a class for which to find options.
	 * @return The defined debug options; an empty set will be created if not already defined.
	 */
	public ClassDebugOptions getClassOptions(ClassReflector reflector) {
		Objects.requireNonNull(reflector, "reflector");
		PackageClassLocator locator = new PackageClassLocator(reflector.getClass());
		return fetchClassOptions(locator);
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
