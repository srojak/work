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

import srojak.utest.TestIdentifier;

/**
 * @author Stephen
 *
 */
public class SingleNameTestIdentifier 
		implements TestIdentifier {
	private final String _strName;
	
	public SingleNameTestIdentifier(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_strName = strName;
	}

	@Override
	public String getText() {
		return _strName;
	}

	@Override
	public TestInstanceIdentifier createInstance() {
		return new TestInstanceIdentifier(_strName);
	}
}
