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
package srojak.core;

import srojak.core.impl.NameTokenCommon;
import srojak.core.impl.NameTokenFixed;

/**
 * @author Stephen
 *
 */
public interface NameToken
		extends INamed, StringComparable, Comparable<NameToken> {
	int hashCode();
	boolean equals(Object obj);
	boolean isRestricted();

	@SuppressWarnings("exports")
	public static final NameTokenFixed Anon = new NameTokenFixed("anon");
	
	public static NameToken factory(String strName) {
		return new NameTokenCommon(strName);
	}
	
	public static NameToken classNameFactory(Class<?> classObj) {
		return new NameTokenCommon(classObj.getSimpleName());
	}
	
	public static NameToken factory(NameToken tokenBase, String strExtend) {
		return NameTokenCommon.extend(tokenBase, strExtend);
	}
}
