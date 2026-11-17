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

import srojak.collate.cmprule.CollationComparisonBasis;
import srojak.collate.cmprule.CollationComparisonRuleKey;
import srojak.core.Equatable;
import srojak.core.Named;

/**
 * @author Stephen
 *
 */
public sealed interface CollationComparisonRule
		extends Named, Equatable<CollationComparisonRule>
		permits CollationComparisonRuleKey {
	
	CollationComparisonBasis basis();
	int flags();
	boolean isBinary();
	boolean isCodePointBinary();
	boolean usesCodePage();
	boolean usesCollator();
	boolean isAccentSensitive();
	boolean isCaseSensitive();
	boolean isIdentical();
	boolean isSQLServerLegacy();
	
	static CollationComparisonRule forName(String strName) {
		return CollationComparisonRuleKey.forName(strName);
	}
}
