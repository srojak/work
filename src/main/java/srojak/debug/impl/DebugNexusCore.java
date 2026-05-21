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

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.debug.DebugProperties;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;
/**
 * @author Stephen
 *
 */
public class DebugNexusCore {
	private static final HashMap<DebugSwitchKey, DebugSwitchContent> _table;
	private static final DebugProperties _properties;
	public static final String PROPERTIES_FILE_NAME;
	public static final DateTimeFormatter FORMAT_TIME_STAMP;
	private static ObservationWriter _writer;
	private static ObsLevel _levelDefault;
	private static boolean _bAutoFlush;
	
	static {
		_table = new HashMap<DebugSwitchKey, DebugSwitchContent>();
		_properties = new DebugProperties();
		PROPERTIES_FILE_NAME = "debug.properties";
		_writer = new ObservationWriterLevelFilterPrintStream(System.err);
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
		_levelDefault = ObsLevel.WARN;
		_bAutoFlush = false;
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
	
	public static void putContent(DebugSwitchContent content) {
		_table.put(content.getKey(), content);
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
