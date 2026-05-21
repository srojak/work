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

import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchKeyClassSubject 
		extends DebugSwitchKeyBase {
	private final String _strSubject;

	/**
	 * @param classOwner
	 */
	public DebugSwitchKeyClassSubject(Class<?> classOwner, String strSubject) {
		super(classOwner, strSubject);
		_strSubject = strSubject;
	}
	
	public DebugSwitchKeyClassSubject(Object objOwner, String strSubject) {
		super(objOwner.getClass(), strSubject);
		_strSubject = strSubject;
	}
	
	public DebugSwitchKeyClassSubject(PackageClassLocator locator, String strSubject) {
		super(locator, strSubject);
		_strSubject = strSubject;
	}

	@Override
	public boolean hasSubjectName() {
		return true;
	}

	@Override
	public String getSubjectName() {
		return _strSubject;
	}

	@Override
	public String toString() {
		StringBuilder sb = super.buildClassInfo();
		sb.append(", subj=");
		sb.append(_strSubject);
		sb.append(')');
		return sb.toString();
	}
}
