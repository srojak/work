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

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.observe.HasObsLevel;
import srojak.core.observe.HasSingleObservationWriter;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterContainerBase;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.specialized.IntegerCounter;
import srojak.debug.impl.ClassDebugOptionEntry;
import srojak.debug.impl.ClassDebugOptionMap;
import srojak.debug.impl.DebugNexusCore;
import srojak.debug.impl.DebugSwitchContent;

/**
 * @author Stephen
 *
 * The public class through which to read debug switches and options.
 */
public final class DebugSwitchReader 
		extends ObservationWriterContainerBase
		implements HasSingleObservationWriter, HasObsLevel {
	private ObsLevel _levelWrite;

	/**
	 * @param writer
	 */
	public DebugSwitchReader(ObservationWriter writer) {
		super(writer);
		_levelWrite = ObsLevel.INFO;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _levelWrite;
	}

	@Override
	public void setObsLevel(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		_levelWrite = level;
	}
	
	private List<DebugSwitchKey> getSortedSwitchKeys() {
		return DebugNexusCore.getAllSwitchKeysAsStream().sorted().toList();
	}
	
	private List<DebugSwitchKey> getSortedSwitchKeysForPackage(String strPackage) {
		return DebugNexusCore.getAllSwitchKeysAsStream().filter(k -> k.getClassLocator().getPackageName().equals(strPackage))
				.sorted().toList();
	}
	
	private void enumOptionsForClass(StringBuilder sb, PackageClassLocator locClass) {
		ClassDebugOptionMap mapOptions = DebugNexusCore.getOptionsForClass(locClass);
		if (mapOptions != null) {
			List<DebugOptionNameValue> listOptions = mapOptions.getOptions();
			// can't sort this way
			//listOptions.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
			sb.append("\n    options:");
			Iterator<DebugOptionNameValue> iterator = listOptions.iterator();
			while (iterator.hasNext()) {
				ClassDebugOptionEntry option = (ClassDebugOptionEntry) iterator.next();
				sb.append("\n      ");
				sb.append(option.getName());
				sb.append(", value=");
				sb.append(option.getValue());
			}
		}
	}
	
	private int enumSwitches(StringBuilder sb, List<DebugSwitchKey> listKeys, 
			Consumer<PackageClassLocator> consumerOptions) {
		IntegerCounter counter = new IntegerCounter();
		Iterator<DebugSwitchKey> iterator = listKeys.iterator();
		while (iterator.hasNext()) {
			DebugSwitchKey key = iterator.next();
			DebugSwitchContent swDebug = DebugNexusCore.getContent(key);
			sb.append("\n  ");
			if (key.hasSubjectName()) {
				sb.append("  ");
			}
			sb.append(swDebug);
			if (!key.hasSubjectName()) {
				consumerOptions.accept(key.getClassLocator());
			}
			counter.increment();
		}
		sb.append("\n  ");
		sb.append(counter.getValue());
		sb.append(" switches");	
		return counter.getValue();
	}
		
	public int enumerateAllSwitches() {
		StringBuilder sb = new StringBuilder("switches:");
		List<DebugSwitchKey> listKeys = getSortedSwitchKeys();
		int nSwitches = enumSwitches(sb, listKeys, loc -> { });
		ObservationWriter writer = getObservationWriter();
		writer.write(_levelWrite, sb.toString());
		return nSwitches;
	}
	
	public int enumerateAllSwitchesForPackage(String strPackage) {
		Objects.requireNonNull(strPackage, "strPackage");
		StringBuilder sb = new StringBuilder("switches for package");
		sb.append(strPackage);
		sb.append(":");
		List<DebugSwitchKey> listKeys = getSortedSwitchKeysForPackage(strPackage);
		int nSwitches =enumSwitches(sb, listKeys, loc -> { });
		ObservationWriter writer = getObservationWriter();
		writer.write(_levelWrite, sb.toString());
		return nSwitches;
	}
	
	public int enumerateAllSwitchesAndOptions() {
		StringBuilder sb = new StringBuilder("switches:");
		List<DebugSwitchKey> listKeys = getSortedSwitchKeys();
		int nSwitches =enumSwitches(sb, listKeys, loc -> enumOptionsForClass(sb, loc));
		ObservationWriter writer = getObservationWriter();
		writer.write(_levelWrite, sb.toString());
		return nSwitches;
	}
}
