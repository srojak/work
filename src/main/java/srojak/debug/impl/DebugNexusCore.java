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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import srojak.core.TextMessageRelay;
import srojak.core.io.PrintStreamTextRelay;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugProperties;
import srojak.debug.DebugPropertyKeys;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchKeyClass;
/**
 * @author Stephen
 *
 */
public class DebugNexusCore
		implements DebugPropertyKeys {
	private static final HashMap<DebugSwitchKey, DebugSwitchContent> _table;
	private static final HashMap<PackageClassLocator, ClassDebugOptionMap> _mapClassOptions;
	private static final DebugProperties _properties;
	public static final String PROPERTIES_FILE_NAME;
	public static final DateTimeFormatter FORMAT_TIME_STAMP;
	private static ObservationWriter _writer;
	private static ObsLevel _levelDefault;
	private static boolean _bAutoFlush;
	private static SwitchCaptureList _listCapture;
	
	static {
		_table = new HashMap<DebugSwitchKey, DebugSwitchContent>();
		_mapClassOptions = new HashMap<PackageClassLocator, ClassDebugOptionMap>();
		_properties = new DebugProperties();
		PROPERTIES_FILE_NAME = "debug.properties";
		_writer = new ObservationWriterLevelFilterPrintStream(System.err);
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
		_levelDefault = ObsLevel.WARN;
		_bAutoFlush = false;
		_listCapture = null;
	}
	
	public static DebugProperties getProperties() {
		return _properties;
	}
	
	public static boolean getAutoFlush() {
		return _bAutoFlush;
	}
	
	public static void setAutoFlush(boolean bState) {
		_bAutoFlush = bState;
	}
	
	public static DebugSwitchContent getContent(DebugSwitchKey key) {
		return _table.get(key);
	}
	
	public static void startConfigFile(Path pathFile) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writer.writeDiagnostic("starting file " + pathFile);
		}
	}
	
	public static void endConfigFile(Path pathFile) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writer.writeDiagnostic("completed file " + pathFile);
		}
	}
	
	public static void diagnosticTableWalk(TextMessageRelay relay) {
		relay.writeln("currently defined switches");
		_table.forEach((k, c) -> {
			relay.writeln(c.toString());
		});
	}
	
	private static void putNewContent(DebugSwitchContent content, Supplier<String> supplierDiagnostic) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writer.writeDiagnostic(supplierDiagnostic.get());
		}
		_table.put(content.getKey(), content);
	}
	
	public static void putContent(DebugSwitchContent content) {
		if (_listCapture != null) {
			putNewContent(content, () -> "creating new DebugSwitch for " + content.getKey()
				+ " from loading " + _listCapture.getStartSwitch().getKey());
			_listCapture.addToList(content);
		} else {
			putNewContent(content, () -> "creating new DebugSwitch for " + content.getKey());
		}
	}
	
	public static int getSwitchCount() {
		return _table.size();
	}
	
	public static DebugSwitch[] getAllSwitches() {
		return _table.values().toArray(new DebugSwitch[0]);
	}
	
	public static Stream<DebugSwitchKey> getAllSwitchKeysAsStream() {
		return _table.keySet().stream();
	}
	
	public static ClassDebugOptionMap getOptionsForClass(PackageClassLocator locClass) {
		return _mapClassOptions.get(locClass);
	}
	
	public static void enableBaseClassSwitches(DebugSwitchKey keyClass) {
		TextMessageRelay relayErr = new PrintStreamTextRelay(System.err);
		boolean bDiagCascade = _properties.isPropertyValueYesOrTrue(DIAG_SWITCH_CASCADE);
		DebugSwitchContent swClass = _table.get(keyClass);
		if (swClass == null) {
			return;
		}
		ObsLevel levelClass = swClass.getLevel();
		try {
			_listCapture = new SwitchCaptureList(swClass);
			Class<?> classLeaf = Class.forName(keyClass.getFullName());
			Class<?> classBase = classLeaf.getSuperclass();
			while (classBase != null) {
				PackageClassLocator locatorBase = new PackageClassLocator(classBase);
				if (locatorBase.isJavaClass()) {
					// no point in continuing
					break;
				}
				DebugSwitchKey keyBase = new DebugSwitchKeyClass(locatorBase);
				DebugSwitchContent swBase = _table.get(keyBase);
				if (swBase == null) {
					swBase = new DebugSwitchContent(keyBase);
					swBase.setLevel(swClass.getLevel());
					swBase.setShowSourceLocations(swClass.showSourceLocations());
					putNewContent(swBase, () -> "creating new DebugSwitch for " + keyBase
							+ " cascading from " + keyClass);
				} else if (_listCapture.isInList(swBase)) {
					if (bDiagCascade) {
						_writer.writeDiagnostic("found DebugSwitch in capture list for " + keyBase);
					}
					if (!swBase.isLevelAtLeast(levelClass)) {
						swBase.setLevel(levelClass);
					}
					if (swClass.showSourceLocations()) {
						swBase.setShowSourceLocations(true);
					}
				} else {
					if (!swBase.isLevelAtLeast(levelClass)) {
						swBase.setLevel(levelClass);
					}
				}
				classBase = classBase.getSuperclass();
			}
		} catch (ClassNotFoundException exc) {
			_writer.writeDiagnostic("unexpected ClassNotFoundException: " + exc.getMessage());
		} finally {
			_listCapture = null;
		}
	}
	
	public static ClassDebugOptionMap createOptionsForClass(PackageClassLocator locClass) {
		if (_properties.isDiagNewClassOptionsEnabled()) {
			_writer.writeDiagnostic("creating new class options for " + locClass);
		}
		ClassDebugOptionMap entry = new ClassDebugOptionMap(locClass);
		_mapClassOptions.put(entry.getOwner(), entry);
		return entry;
	}
	
	public static int getClassOptionSetsCount() {
		return _mapClassOptions.size();
	}
	
	public static Stream<PackageClassLocator> getAllClassOptionKeysAsStream() {
		return _mapClassOptions.keySet().stream();
	}
	
	public static ObservationWriter getWriter() {
		return _writer;
	}
	
	public static void setWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}
	
	public static ObsLevel getDefaultLogLevel() {
		return _levelDefault;
	}
	
	public static void setDefaultLogLevel(ObsLevel level) {
		_levelDefault = level;
	}
	
	protected static void writeln(ObsLevel level, String strText) {
		_writer.write(level, strText);
		if (_bAutoFlush) {
			_writer.flush();
		}
	}

	protected static void writeDiagnostic(String strText) {
		_writer.writeDiagnostic(strText);
		if (_bAutoFlush) {
			_writer.flush();
		}
	}
}
