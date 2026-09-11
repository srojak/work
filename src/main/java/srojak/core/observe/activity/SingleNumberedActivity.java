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

import srojak.core.observe.ObservedActivity;

/**
 * @author Stephen
 *
 */
public class SingleNumberedActivity 
		extends SingleActivity {
	private int _sequence;

	/**
	 * @param strActivity
	 */
	public SingleNumberedActivity(String strActivity) {
		super(strActivity);
		_sequence = 0;
	}

	public ObservedActivity instance(int nSequence) {
		_sequence = nSequence;
		return this;
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return String.format("%s (%d)", _activity, _sequence);
	}
}
