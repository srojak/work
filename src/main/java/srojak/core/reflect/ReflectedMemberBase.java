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
package srojak.core.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public abstract class ReflectedMemberBase {

	protected static final ObservedActivity _activitySetAccess = new SingleActivity("set accessible");
	
	/**
	 * 
	 */
	public ReflectedMemberBase() {
		
	}
	
	protected abstract Member getMember();

	public Class<?> getDeclaringClass() {
		return getMember().getDeclaringClass();
	}

	public String getName() {
		return getMember().getName();
	}

	public int getModifiers() {
		return getMember().getModifiers();
	}

	public boolean isPublic() {
		return BitMethods.test(getMember().getModifiers(), Modifier.PUBLIC);
	}
	
	public String metadataToString() {
		return getMember().toString();
	}
}
