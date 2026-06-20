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
package srojak.events;

import java.util.Objects;

import srojak.core.events.CoreEvent;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CollectionChangeEvent 
		extends CoreEvent {
	private final int _nVerb;
	private final Object _obj;
	
	/**
	 * Verb values
	 */
	public static final int VERB_CLEAR = 0;
	public static final int VERB_ADD = 1;
	public static final int VERB_ADD_MULT = 2;
	public static final int VERB_REMOVE = 3;
	public static final int VERB_REMOVE_MULT = 4;
	public static final int VERB_SET_ITEM = 5;
	public static final int VERB_REPLACE_MULT = 6;
	public static final int VERB_SORT = 7;

	public CollectionChangeEvent(Object source, int verb) {
		super(source);
		_nVerb = verb;
		_obj = null;
	}
	
	public CollectionChangeEvent(Object source, int verb, Object objChange) {
		super(source);
		Objects.requireNonNull(objChange, "objChange");
		_nVerb = verb;
		_obj = objChange;
	}

	public int getVerb() {
		return _nVerb;
	}
	
	public boolean hasChangeObject() {
		return _obj == null;
	}
	
	public Object getChangeObject() {
		return _obj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getChangeObjectAs() {
		return (T) _obj;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append( ", verb = ");
		sb.append(_nVerb);
	}
}
