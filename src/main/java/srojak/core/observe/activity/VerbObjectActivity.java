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
package srojak.core.observe.activity;

import java.util.Objects;

import srojak.core.observe.ObservedActivity;

/**
 * @author Stephen
 *
 */
public class VerbObjectActivity 
		implements ObservedActivity {
	private final String _verb;
	private String _object;
	
	private static final String NULL = "(null)";
	
	public VerbObjectActivity(String strVerb, Object objObject) {
		Objects.requireNonNull(strVerb, "strVerb");
		if (strVerb.isBlank()) {
			throw new IllegalArgumentException("strVerb is blank");
		}
		_verb = strVerb;
		_object = objObject == null ? NULL : objObject.toString(); 
	}
	
	public void changeObject(Object objNew) {
		_object = objNew == null ? NULL : objNew.toString(); 
	}

	@Override
	public String describe() {
		return _verb + " " + _object;
	}
}
