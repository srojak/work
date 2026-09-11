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

/**
 * @author Stephen
 *
 */
public class PhasedActivity
		extends SingleActivity {
	private String _phase;

	/**
	 * @param strActivity
	 */
	public PhasedActivity(String strActivity) {
		super(strActivity);
		_phase = null;
	}
	
	public PhasedActivity(String strActivity, String strPhase) {
		super(strActivity);
		_phase = strPhase;
	}

	public void setPhase(String strPhase) {
		_phase = strPhase;
	}

	@Override
	public String describe() {
		if (_phase == null) {
			return _activity;
		} else {
			return _activity + "/" + _phase;
		}
	}
}
