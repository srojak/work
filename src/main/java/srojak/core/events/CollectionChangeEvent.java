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
package srojak.core.events;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CollectionChangeEvent 
		extends CoreEvent {
	private final int _nVerb;
	
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

	/**
	 * @param source
	 */
	public CollectionChangeEvent(Object source, int verb) {
		super(source);
		_nVerb = verb;
	}

	public int getVerb() {
		return _nVerb;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append( ", verb = ");
		sb.append(_nVerb);
	}
}
