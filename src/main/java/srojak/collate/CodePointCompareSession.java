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
package srojak.collate;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class CodePointCompareSession {
	private final String _primary;
	private final String _comparison;
	private final int _szPrimary;
	private final int _szComparison;
	private int _idxPrimary;
	private int _idxComparison;

	/**
	 * 
	 */
	public CodePointCompareSession(String strPrimary, String strComparison) {
		Objects.requireNonNull(strPrimary, "strPrimary");
		Objects.requireNonNull(strComparison, "strComparison");
		_primary = strPrimary;
		_comparison = strComparison;
		_szPrimary = _primary.length();
		_szComparison = _comparison.length();
		_idxPrimary = 0;
		_idxComparison = 0;
	}

	public int compareFromIndex(int idxFrom) {
		Objects.checkIndex(idxFrom, _szPrimary);
		_idxPrimary = idxFrom;
		_idxComparison = 0;
		while (_idxPrimary < _szPrimary & _idxComparison < _szComparison) {
			int cpPrimary = _primary.codePointAt(_idxPrimary);
			int cpComparison = _comparison.codePointAt(_idxComparison);
			if (cpPrimary != cpComparison) {
				return cpPrimary - cpComparison;
			}
			_idxPrimary += Character.charCount(cpPrimary);
			_idxComparison += Character.charCount(cpComparison);
		}
		return 0;
	}
	
	public int findIndexFrom(int idxFrom) {
		Objects.checkIndex(idxFrom, _szPrimary);
		int cpFindFirst = _comparison.codePointAt(0);
		int idxStart = idxFrom;
		while (idxStart < _szPrimary) {
			int cpStart = _primary.codePointAt(idxStart);
			if (cpStart == cpFindFirst) {
				if (compareFromIndex(idxStart) == 0) {
					return idxStart;
				}
			}
			idxStart += Character.charCount(cpStart);
		}
		return -1;
	}
	
	public int applyLengthDifferences() {
		// handles proper substrings
		return (_szPrimary - _idxPrimary) - (_szComparison - _idxComparison);
	}
}
