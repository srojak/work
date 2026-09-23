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
package srojak.debug.tools;

import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugContentVisitor;
import srojak.debug.DebugOptionNameValue;
import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 */
public class DebugContentCountingVisitor 
		implements DebugContentVisitor {
	private int _nClassSwitches;
	private int _nSubjectSwitches;
	private int _nOptions;
	
	public DebugContentCountingVisitor() {
		_nClassSwitches = 0;
		_nSubjectSwitches = 0;
		_nOptions = 0;
	}
	
	public int totalClassSwitches() {
		return _nClassSwitches;
	}
	
	public int totalSubjectSwitches() {
		return _nSubjectSwitches;
	}
	
	public int totalOptions() {
		return _nOptions;
	}
	
	public void reset() {
		_nClassSwitches = 0;
		_nSubjectSwitches = 0;
		_nOptions = 0;
	}

	@Override
	public void visitClassSwitch(DebugSwitch sw) {
		_nClassSwitches++;
	}

	@Override
	public void visitSubjectSwitch(DebugSwitch sw) {
		_nSubjectSwitches++;
	}

	@Override
	public void visitClassOption(DebugOptionNameValue option) {
		_nOptions++;
	}

	@Override
	public void endClass(PackageClassLocator locator) {
		// does nothing here
	}
}
