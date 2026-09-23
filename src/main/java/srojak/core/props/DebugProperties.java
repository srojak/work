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
package srojak.core.props;

import srojak.core.events.ActionCompletedOriginator;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
/**
 * @author Stephen
 *
 */
public class DebugProperties
		extends PropertySetBase
		implements PropertiesReadOnly, DebugPropertyKeys, ActionCompletedOriginator {
	private final FlagsShort _flags;
	
	public static final String PROPERTIES_FILE_NAME = "debug.properties";
	public static final short FLAGS_DIAG_NEW_SWITCH = 0x1;
	public static final short FLAGS_DIAG_NEW_CLASS_OPTIONS = 0x2;
	public static final short FLAGS_INFER_SHOW_LOCATION_RULE = 0x4;
	public static final short FLAGS_DIAG_SWITCH_CASCADE = 0x8;
	public static final short FLAGS_DIAG_SHUTDOWN = 0x10;
	
	public DebugProperties() {
		super();
		_flags = new FlagsShort();
		_flags.set(FLAGS_INFER_SHOW_LOCATION_RULE);
	}
	
	@Override
	protected void postLoad() {
		_flags.apply(evalBooleanProperty(DIAG_NEW_SWITCH,  false), FLAGS_DIAG_NEW_SWITCH);
		_flags.apply(evalBooleanProperty(DIAG_NEW_CLASS_OPTIONS, false), FLAGS_DIAG_NEW_CLASS_OPTIONS);
		_flags.apply(evalBooleanProperty(DIAG_SWITCH_CASCADE, false), FLAGS_DIAG_SWITCH_CASCADE);
		_flags.apply(evalBooleanProperty(DIAG_SHUTDOWN, false), FLAGS_DIAG_SHUTDOWN);
	}
	
	public FlagsShortTest getFlags() {
		return _flags;
	}
	
	public boolean isDiagNewSwitchEnabled() {
		return _flags.test(FLAGS_DIAG_NEW_SWITCH);
	}
	
	public boolean isDiagNewClassOptionsEnabled() {
		return _flags.test(FLAGS_DIAG_NEW_CLASS_OPTIONS);
	}
	
	public boolean isRuleInferShowLocations() {
		return _flags.test(FLAGS_INFER_SHOW_LOCATION_RULE);
	}
	
	public boolean isDiagSwitchCascade() {
		return _flags.test(FLAGS_DIAG_SWITCH_CASCADE);
	}
	
	public boolean isDiagShutdownEnabled() {
		return _flags.test(FLAGS_DIAG_SHUTDOWN);
	}
}
