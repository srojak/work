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
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugContentVisitor;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;

/**
 * @author Stephen
 *
 */
public class ClassDebugStore {
	private final PackageClassLocator _locator;
	private final DebugSwitchContent _swClass;
	private final ClassDebugOptionMap _mapOptions;
	private final HashMap<String, DebugSwitchContent> _mapSubjects;
	
	private static final DebugSwitchContentComparatorByKey SWITCH_COMPARATOR
		= new DebugSwitchContentComparatorByKey();
	private static final ClassDebugOptionEntryComparatorByName OPTION_ENTRY_COMPARATOR
		= new ClassDebugOptionEntryComparatorByName();
	
	public ClassDebugStore(DebugSwitchContent swClass) {
		Objects.requireNonNull(swClass, "swClass");
		_swClass = swClass;
		_locator = _swClass.getClassLocator();
		_mapOptions = new ClassDebugOptionMap(_locator);
		_mapSubjects = new HashMap<String, DebugSwitchContent>();
	}
	
	public PackageClassLocator getLocator() {
		return _locator;
	}
	
	public DebugSwitchContent getClassSwitch() {
		return _swClass;
	}
	
	public ClassDebugOptionMap getOptions() {
		return _mapOptions;
	}
	
	public int getSwitchCount() {
		return 1 + _mapSubjects.size();
	}
	
	public DebugSwitchContent getSubjectSwitch(String strSubject) {
		return _mapSubjects.get(strSubject);
	}
	
	public DebugSwitchContent getSelectedSwitch(DebugSwitchKey key) {
		if (key.hasSubjectName()) {
			return _mapSubjects.get(key.getSubjectName());
		} else {
			return _swClass;
		}
	}
	
	public void putSubjectSwitch(String strSubject, DebugSwitchContent swSubject) {
		Objects.requireNonNull(strSubject, "strSubject");
		Objects.requireNonNull(swSubject, "swSubject");
		_mapSubjects.putIfAbsent(strSubject, swSubject);
	}
	
	public void setClassOptionsLike(DebugSwitch swPattern) {
		_swClass.setLevel(swPattern.getLevel());
		_swClass.setShowSourceLocations(swPattern.showSourceLocations());
	}
	
	public Set<String> getAllSubjectKeys() {
		return _mapSubjects.keySet();
	}
	
	public void overAllSwitches(Consumer<DebugSwitchContent> consumer) {
		consumer.accept(_swClass);
		_mapSubjects.values().forEach(consumer);
	}
	
	public void visit(DebugContentVisitor visitor) {
		visitor.visitClassSwitch(_swClass);
		_mapSubjects.values().stream().sorted(SWITCH_COMPARATOR).forEach(sw -> {
			visitor.visitSubjectSwitch(sw);
		});
		_mapOptions.getOptionsPrivate().sorted(OPTION_ENTRY_COMPARATOR).forEach(o -> {
			visitor.visitClassOption(o);
		});
		visitor.endClass(_locator);
	}
}
