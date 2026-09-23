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

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import srojak.core.TextMessageRelay;
import srojak.core.backplane.ApplicationBackplane;
import srojak.core.observe.Announcer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.AnnouncerPrintStream;
import srojak.core.props.DebugProperties;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchKeyClass;
/**
 * @author Stephen
 *
 */
public class DebugNexusCore
		implements DebugPropertyKeys {
	private static final HashMap<PackageClassLocator, ClassDebugStore> _mapClasses;
	private static final List<SwitchControlSetRecord> _listControlSets;
	private static final DebugProperties _properties;
	public static final ObservationCollector DEBUG_OBSV;
	public static final DateTimeFormatter FORMAT_TIME_STAMP;
	private static ObservationWriter _writer;
	private static Announcer _announcer;
	private static ObsLevel _levelAnnounce;
	private static ObsLevel _levelDefault;
	private static SwitchCaptureList _listCapture;
	private static SwitchControlSetRecord _ctrlSetActive;
	
	static {
		_mapClasses = new HashMap<PackageClassLocator, ClassDebugStore>();
		_listControlSets = new LinkedList<SwitchControlSetRecord>();
		_properties = ApplicationBackplane.getDebugProperties();
		DEBUG_OBSV = ObservationCollector.makeInstance();
		DebugWriterForwarder forwarder = new DebugWriterForwarder();
		DEBUG_OBSV.addWriterPermanent(forwarder);
		_writer = ApplicationBackplane.WRITER_STD_ERR;
		_announcer = new AnnouncerPrintStream(System.err);
		_levelAnnounce = ObsLevel.WARN;
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
		_levelDefault = ObsLevel.INFO;
		_listCapture = null;
		_ctrlSetActive = null;
		ApplicationBackplane.addShutdownAction(DebugNexusCore::onShutdownCloseWriter);
	}
	
	public static DebugProperties getProperties() {
		return _properties;
	}
	
	public static void readingSwitchControlSet(String strName) {
		SwitchControlSetRecord record = new SwitchControlSetRecord(strName);
		_listControlSets.add(record);
		_ctrlSetActive = record;
		// TODO how can this be announced at startup?
		System.out.println("reading switch control set " + record.getName());
		_writer.writeDiagnostic("reading switch control set \"" + record.getName() + "\"");
	}
	
	public static DebugSwitchContent getContent(DebugSwitchKey key) {
		ClassDebugStore storeClass = _mapClasses.get(key.getClassLocator());
		if (storeClass == null) {
			return null;
		} else {
			return storeClass.getSelectedSwitch(key);
		}
	}
	
	public static DebugSwitchContent createSwitch(DebugSwitchKey key) {
		return new DebugSwitchContent(key, _ctrlSetActive);
	}
	
	private static DebugSwitchContent createClassSwitch(PackageClassLocator locator, Supplier<String> supplierDiagnostic) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writer.writeDiagnostic(supplierDiagnostic.get());
		}
		DebugSwitchKey key = new DebugSwitchKeyClass(locator);
		return new DebugSwitchContent(key, _ctrlSetActive);
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
		_ctrlSetActive = null;
	}
	
	public static void diagnosticTableWalk(TextMessageRelay relay) {
		relay.writeln("currently defined switches");
		_mapClasses.forEach((k, s) -> {
			s.overAllSwitches(c -> relay.writeln(c.toString()));
		});		
	}
	
	private static void putNewContent(DebugSwitchContent content, Supplier<String> supplierDiagnostic) {
		if (_properties.isDiagNewSwitchEnabled()) {
			_writer.writeDiagnostic(supplierDiagnostic.get());
		}
		DebugSwitchKey key = content.getKey();
		boolean isForClass = !key.hasSubjectName();
		ClassDebugStore store = _mapClasses.get(key.getClassLocator());
		if (store == null) {
			// all subject switches must have a class switch
			if (isForClass) {
				store = new ClassDebugStore(content);
			} else {
				DebugSwitchContent contentClass = createClassSwitch(key.getClassLocator(),
						() -> "creating class switch indirectly");
				store = new ClassDebugStore(contentClass);
			}
			_mapClasses.put(key.getClassLocator(), store);
		}
		if (!isForClass) {
			store.putSubjectSwitch(key.getSubjectName(), content);
		}
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
	
	public static List<DebugSwitch> getAllSwitches() {
		List<DebugSwitch> list = new LinkedList<DebugSwitch>();
		_mapClasses.values().forEach(s -> {
			s.overAllSwitches(c -> list.add(c));
		});
		return list;
	}
	
	public static Stream<ClassDebugStore> getAllClassEntries() {
		return _mapClasses.values().stream();
	}
	
	public static ClassDebugStore getClassStore(PackageClassLocator locClass) {
		return _mapClasses.get(locClass);
	}
	
	public static Stream<DebugSwitchKey> getAllSwitchKeysAsStream() {
		// TODO investigate a better way to do this
		List<DebugSwitchKey> list = new LinkedList<DebugSwitchKey>();
		_mapClasses.values().forEach(s -> {
			s.overAllSwitches(c -> list.add(c.getKey()));
		});
		return list.stream();
	}
	
	public static ClassDebugOptionMap getOptionsForClass(PackageClassLocator locClass) {
		ClassDebugStore store = _mapClasses.get(locClass);
		if (store == null) {
			return null;
		} else {
			return store.getOptions();
		}
	}
	
	public static void enableBaseClassSwitches(DebugSwitchKey keyClass) {
		boolean bDiagCascade = _properties.isDiagSwitchCascade();
		ClassDebugStore store = _mapClasses.get(keyClass.getClassLocator());
		if (store == null) {
			return;
		}
		DebugSwitchContent swClass = store.getClassSwitch();
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
				DebugSwitchContent swBase;
				store = _mapClasses.get(locatorBase);
				DebugSwitchKey keyBase = new DebugSwitchKeyClass(locatorBase);
				if (store == null) {
					swBase = createClassSwitch(locatorBase, () -> "creating new DebugSwitch for " + keyBase
							+ " cascading from " + keyClass);
					store = new ClassDebugStore(swBase);
					_mapClasses.put(locatorBase, store);
					store.setClassOptionsLike(swClass);
				} else {
					swBase = store.getClassSwitch();
					if (_listCapture.isInList(swBase)) {
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
		ClassDebugStore storeClass = _mapClasses.get(locClass);
		if (storeClass == null) {
			DebugSwitchContent contentClass = createClassSwitch(locClass, 
				() -> "creating new class switch for " + locClass + " due to options");
			storeClass = new ClassDebugStore(contentClass);
			_mapClasses.put(locClass, storeClass);
		}
		return storeClass.getOptions();
	}
	
	public static int getClassOptionSetsCount() {
		// by definition, this is the number of class switches
		return _mapClasses.size();
	}
	
	public static Stream<PackageClassLocator> getAllClassOptionKeysAsStream() {
		return _mapClasses.keySet().stream();
	}
	
	public static ObservationWriter getWriter() {
		return _writer;
	}
	
	public static void setWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}
	
	public static Announcer getAnnouncer() {
		return _announcer;
	}
	
	public static void setAnnouncer(Announcer announcer) {
		Objects.requireNonNull(announcer, "announcer");
		_announcer = announcer;
	}
	
	public static ObsLevel getAnnounceLevel() {
		return _levelAnnounce;
	}
	
	public static void setAnnounceLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_levelAnnounce = level;
	}
	
	public static ObsLevel getDefaultLogLevel() {
		return _levelDefault;
	}
	
	public static void setDefaultLogLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_levelDefault = level;
	}
	
	protected static void writeln(ObsLevel level, SourceLocation location, String strText) {
		_writer.write(level, location, strText);
		if (_levelAnnounce.isLevelAtLeast(level)) {
			_announcer.announce(level, location);
		}
	}
	
	protected static void writelnException(ObsLevel level, SourceLocation location, Exception exc, String strText) {
		// TODO: pass in activity
		_writer.writeException(level, location, new SingleActivity("?"), exc, false);
		if (_levelAnnounce.isLevelAtLeast(level)) {
			_announcer.announceException(level, location, exc);
		}
	}
	
	protected static void writeStackTrace(ObsLevel level, Throwable t) {
		// TODO: eliminate this because the writers handle it
		StackTraceElement[] frames = t.getStackTrace();
		StringBuilder sb = new StringBuilder("Stack trace:");
		for (StackTraceElement frame : frames) {
			sb.append("\n    ");
			sb.append(frame);
		}
	}

	protected static void writeDiagnostic(String strText) {
		_writer.writeDiagnostic(strText);
	}
	
	protected static void writeDiagnostic(SourceLocation location, String strText) {
		_writer.writeDiagnostic(location, strText);
	}
	
	private static void onShutdownCloseWriter() {
		if (_properties.isDiagShutdownEnabled()) {
			_writer.writeDiagnostic("notified of shutdown");
		}
		if (_writer != ApplicationBackplane.WRITER_STD_ERR) {
			ObservationWriter writerPrior = _writer;
			_writer = ApplicationBackplane.WRITER_STD_ERR;
			try {
				writerPrior.close();
			} catch (IOException exc) {
				_writer.writeDiagnostic("on closing debug writer: " + exc.getMessage());
			}
		}
	}
}
