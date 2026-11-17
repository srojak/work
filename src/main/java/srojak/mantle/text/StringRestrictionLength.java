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
package srojak.mantle.text;

/**
 * @author Stephen
 *
 */
public class StringRestrictionLength 
		extends StringRestrictionCommonBase {
	private final int _maxLength;

	/**
	 * @param strName
	 */
	public StringRestrictionLength(int maxLength) {
		super("length limit to " + String.valueOf(maxLength));
		if (maxLength <= 0) {
			throw new IllegalArgumentException("maxLength must be positive");
		}
		_maxLength = maxLength;
	}

	@Override
	public boolean validate(String strText) {
		return strText != null && strText.length() <= _maxLength;
	}

}
