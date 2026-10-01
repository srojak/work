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
/**
 * @author Stephen
 *
 */
public class DebugProperties
		extends PropertySetBase
		implements DebugPropertyKeys, ActionCompletedOriginator {
	
	public static final String PROPERTIES_FILE_NAME = "debug.properties";
	public static final int FLAGS_DIAG_NEW_SWITCH = 0x2;
	public static final int FLAGS_DIAG_NEW_CLASS_OPTIONS = 0x4;
	public static final int FLAGS_INFER_SHOW_LOCATION_RULE = 0x8;
	public static final int FLAGS_DIAG_SWITCH_CASCADE = 0x10;
	public static final int FLAGS_DIAG_CLOSE = 0x20;
	
	public DebugProperties() {
		super();
		_flags.set(FLAGS_INFER_SHOW_LOCATION_RULE);
	}
	
	@Override
	protected void postLoad() {
		_flags.apply(evalBooleanProperty(DIAG_NEW_SWITCH,  false), FLAGS_DIAG_NEW_SWITCH);
		_flags.apply(evalBooleanProperty(DIAG_NEW_CLASS_OPTIONS, false), FLAGS_DIAG_NEW_CLASS_OPTIONS);
		_flags.apply(evalBooleanProperty(DIAG_SWITCH_CASCADE, false), FLAGS_DIAG_SWITCH_CASCADE);
		_flags.apply(evalBooleanProperty(DIAG_CLOSE, false), FLAGS_DIAG_CLOSE);
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
	
	public boolean isDiagCloseEnabled() {
		return _flags.test(FLAGS_DIAG_CLOSE);
	}
}
