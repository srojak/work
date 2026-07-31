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
package srojak.utest.identifiers;

import java.util.Objects;

import srojak.core.TextBearing;

/**
 * @author Stephen
 *
 */
public class TestInstanceIdentifier
		implements TextBearing {
	private final String _leader;
	private final String _text;
	
	public TestInstanceIdentifier(String strLeader, String strText) {
		Objects.requireNonNull(strLeader, "strLeader");
		if (strLeader.isBlank()) {
			throw new IllegalArgumentException("strLeader is blank");
		}
		Objects.requireNonNull(strText, "strText");
		if (strText.isBlank()) {
			throw new IllegalArgumentException("strText is blank");
		}
		_leader = strLeader;
		_text = strText;
	}
	
	public TestInstanceIdentifier(String strText) {
		Objects.requireNonNull(strText, "strText");
		if (strText.isBlank()) {
			throw new IllegalArgumentException("strText is blank");
		}
		_leader = "test";
		_text = strText;
	}

	@Override
	public String getText() {
		return _leader + " " + _text;
	}
}
