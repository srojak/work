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
public class SingleActivity 
		implements ObservedActivity {
	protected final String _activity;
	
	public SingleActivity(String strActivity) {
		Objects.requireNonNull(strActivity, "strActivity");
		if (strActivity.isBlank()) {
			throw new IllegalArgumentException("strActivity is blank");
		}
		_activity = strActivity;
	}

	@Override
	public String describe() {
		return _activity;
	}
}
