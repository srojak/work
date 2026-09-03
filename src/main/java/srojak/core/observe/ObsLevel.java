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
package srojak.core.observe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import srojak.core.Named;

/**
 * defines an ordered set of observation levels.
 * 
 * @author Stephen
 *
 */
public class ObsLevel 
		implements Named, Comparable<ObsLevel> {
	private final int _level;
	private final String _strName;
	
	/**
	 * The level at which no entries would be received when filtering.
	 * Not valid for origination.
	 */
	public static final ObsLevel NONE;
	
	/**
	 * Fatal to the program.
	 */
	public static final ObsLevel FATAL;
	
	/**
	 * Severe but not fatal.
	 */
	public static final ObsLevel SEVERE;
	
	/**
	 * Triggers the alert mechanism.
	 */
	public static final ObsLevel ALERT;
	
	/**
	 * High importance but not indicative of problems.
	 */
	public static final ObsLevel NOTICE;
	
	/**
	 * Notifications that an error has occurred.
	 */
	public static final ObsLevel ERROR;
	
	/**
	 * Warning messages.
	 */
	public static final ObsLevel WARN;
	
	/**
	 * Low importance informational messages.
	 */
	public static final ObsLevel INFO;
	
	/**
	 * Detail level informational messages.
	 */
	public static final ObsLevel DETAIL;
	
	/**
	 * Highest level trace messages.
	 */
	public static final ObsLevel TRACE;
	
	/**
	 * Highest level debug messages.
	 */
	public static final ObsLevel DEBUG;
	
	/**
	 * Mid-level trace messages.
	 */
	public static final ObsLevel TRACE2;
	
	/**
	 * Mid-level debug messages.
	 */
	public static final ObsLevel DEBUG2;
	
	/**
	 * Low-level trace messages.
	 */
	public static final ObsLevel TRACE3;
	
	/**
	 * Low-level debug messages.
	 */
	public static final ObsLevel DEBUG3;
	
	/**
	 * A debug level below {@code DEBUG3}.
	 */
	public static final ObsLevel FINE;
	
	/**
	 * A debug level below {@code FINE}.
	 */
	public static final ObsLevel FINER;
	
	/**
	 * The lowest predefined debug level.
	 */
	public static final ObsLevel FINEST;
	
	private static final HashMap<String, ObsLevel> mapAll;
	
	static {
		ArrayList<ObsLevel> list = new ArrayList<ObsLevel>();
		NONE = makeLevel(list, "NONE", 0);
		FATAL = makeLevel(list, "FATAL", 0100);
		SEVERE = makeLevel(list, "SEVERE", 0200);
		ALERT = makeLevel(list, "ALERT", 0300);
		NOTICE = makeLevel(list, "NOTICE", 0400);
		ERROR = makeLevel(list, "ERROR", 0500);
		WARN = makeLevel(list, "WARN", 0600);
		INFO = makeLevel(list, "INFO", 0700);
		DETAIL = makeLevel(list, "DETAIL", 01000);
		TRACE = makeLevel(list, "TRACE", 01100);
		DEBUG = makeLevel(list, "DEBUG", 01200);
		TRACE2 = makeLevel(list, "TRACE2", 01300);
		DEBUG2 = makeLevel(list, "DEBUG2", 01400);
		TRACE3 = makeLevel(list, "TRACE3", 01500);
		DEBUG3 = makeLevel(list, "DEBUG3", 01600);
		FINE = makeLevel(list, "FINE", 02000);
		FINER = makeLevel(list, "FINER", 02400);
		FINEST = makeLevel(list, "FINEST", 03000);
		HashMap<String, ObsLevel> map = new HashMap<String, ObsLevel>();
		for (ObsLevel level : list) {
			map.put(level.getName(), level);
		}
		mapAll = map;
	}
	
	private static ObsLevel makeLevel(ArrayList<ObsLevel> list, String strName, int nLevel) {
		ObsLevel level = new ObsLevel(nLevel, strName);
		list.add(level);
		return level;
	}
	
	private ObsLevel(int nLevel, String strName) {
		_level = nLevel;
		_strName = strName;
	}

	/**
	 * gets the text name of the level.
	 */
	@Override
	public String getName() {
		return _strName;
	}
	
	/**
	 * gets the ordinal value assigned to the level.
	 * ordinal values are numerically decreasing.
	 * @return the assigned ordinal value.
	 */
	public int getLevel() {
		return _level;
	}
	
	/**
	 * compares the ordinal value to that of another {@code ObsLevel} object.
	 * @param other the other object to which to compare.
	 * @return {@value true} if this object has an ordinal value at or above
	 * that of {@code other}.
	 */
	public boolean isLevelAtLeast(ObsLevel other) {
		return _level >= other._level;
	}
	
	public boolean isValidForWriting() {
		return _level > 0;
	}
	
	@Override
	public int hashCode() {
		return _level;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof ObsLevel other) {
			return _level == other._level;
		} else
			return false;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
	 *
	 * Order is from highest severity down to lowest.
	 * 
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *          
     * @throws NullPointerException if the specified object is null
	 */
	@Override
	public int compareTo(ObsLevel o) {
		Objects.requireNonNull(o, "o");
		return Integer.compare(_level, o._level);
	}

	@Override
	public String toString() {
		return "ObsLevel[" + _strName + "]";
	}
	
	/**
	 * parse a string, looking for the {@code ObsLevel} instance of that name.
	 * @param strInput the name being sought.
	 * @return the {@code ObsLevel} instance of that name.
	 */
	public static ObsLevel parse(String strInput) {
		ObsLevel level = mapAll.get(strInput);
		if (level == null) {
			throw new InvalidObservationLevelException("\"" + strInput + "\" not recognized");
		}
		return level;
	}
	
	/**
	 * get all known {@code ObsLevel} instances.
	 * @return an immutable {@code List} of all instance.
	 */
	public static List<ObsLevel> getAllKnown() {
		ArrayList<ObsLevel> list = new ArrayList<ObsLevel>(mapAll.values());
		list.sort((a, b) -> a.compareTo(b));
		return List.copyOf(list);
	}
	
	/**
	 * is there an {@code ObsLevel} instance having the given name?
	 * @param strName the name being sought.
	 * @return {@value true} if the name is defined.
	 * @throws NullPointerException if the name is {@value null}.
	 */
	public static boolean isLevelDefined(String strName) {
		Objects.requireNonNull(strName, "strName");
		return mapAll.containsKey(strName);
	}
	
	/**
	 * define a new {@code ObsLevel} instance.
	 * @param strName the name of the instance, which must be unique.
	 * @param nLevel the ordinal level of the instance.
	 * @return the new instance.
	 * @throws NullPointerException if the name is {@value null}.
	 * @throws IllegalArgumentException if the name is empty or is already defined.
	 * @throws IllegalArgumentException if the level is nonpositive or is already assigned
	 * 		to another {@code ObsLevel}.
	 */
	public static ObsLevel defineLevel(String strName, int nLevel) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isEmpty()) {
			throw new IllegalArgumentException("strName is empty");
		}
		if (nLevel <= 0) {
			throw new IllegalArgumentException("nLevel is nonpositive");
		}
		if (isLevelDefined(strName)) {
			throw new IllegalArgumentException("strName already defined");
		};
		for (ObsLevel levelExist : mapAll.values()) {
			if (levelExist.getLevel() == nLevel) {
				throw new IllegalArgumentException("nLevel already assigned");
			}
		}
		ObsLevel level = new ObsLevel(nLevel, strName);
		mapAll.put(level.getName(), level);
		return level;
	}
	
	public static void validateEventLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		if (!level.isValidForWriting()) {
			throw new IllegalArgumentException("level not valid");
		}
	}
}
