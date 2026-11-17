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
package srojak.collate.cmprule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.collate.AugmentedCollator;
import srojak.collate.CollationComparisonRule;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public final class CollationComparisonRuleKey 
		implements CollationComparisonRule {
	private final String _name;
	private final CollationComparisonBasis _basis;
	private final int _flags;
	
	private static final Map<String, CollationComparisonRule> _map
		= new HashMap<String, CollationComparisonRule>();
	
	private static final int FLAGS_NONE = 0x0;
	private static final int FLAGS_ACCENT = 0x1;
	private static final int FLAGS_CASE = 0x2;
	private static final int FLAGS_IDENTICAL = 0x4;
	private static final int FLAGS_SQL = 0x8;
	
	public static final CollationComparisonRule BIN;
	public static final CollationComparisonRule CDPT;
	public static final CollationComparisonRule CI_AI;
	public static final CollationComparisonRule CI_AS;
	public static final CollationComparisonRule CS_AS;
	public static final CollationComparisonRule IDENT;
	public static final CollationComparisonRule SQL_CPG_CI_AI;
	public static final CollationComparisonRule SQL_CPG_CI_AS;
	public static final CollationComparisonRule SQL_CPG_CS_AS;
	
	static {
		BIN = new CollationComparisonRuleKey("BIN", CollationComparisonBasis.BINARY);
		define(BIN);
		
		CDPT = new CollationComparisonRuleKey("CDPT", CollationComparisonBasis.CODE_POINT);
		define(CDPT);
		
		CI_AI = new CollationComparisonRuleKey("CI_AI", CollationComparisonBasis.COLLATOR);
		define(CI_AI);
		
		CI_AS = new CollationComparisonRuleKey("CI_AS", CollationComparisonBasis.COLLATOR, FLAGS_ACCENT);
		define(CI_AS);
		
		CS_AS = new CollationComparisonRuleKey("CS_AS", CollationComparisonBasis.COLLATOR, FLAGS_ACCENT|FLAGS_CASE);
		define(CI_AS);
		
		IDENT = new CollationComparisonRuleKey("IDENT", CollationComparisonBasis.COLLATOR, FLAGS_ACCENT|FLAGS_CASE|FLAGS_IDENTICAL);
		define(IDENT);
		
		SQL_CPG_CI_AI = new CollationComparisonRuleKey("SQL_CPG_CI_AI", CollationComparisonBasis.CODE_PAGE, FLAGS_SQL);
		define(SQL_CPG_CI_AI);
		
		SQL_CPG_CI_AS = new CollationComparisonRuleKey("SQL_CPG_CI_AS", 
				CollationComparisonBasis.CODE_PAGE, FLAGS_SQL|FLAGS_ACCENT);
		define(SQL_CPG_CI_AI);
		
		SQL_CPG_CS_AS = new CollationComparisonRuleKey("SQL_CPG_CS_AS", 
				CollationComparisonBasis.CODE_PAGE, FLAGS_SQL|FLAGS_ACCENT|FLAGS_CASE);
		define(SQL_CPG_CI_AI);	
	}
	
	private static void define(CollationComparisonRule rule) {
		_map.put(rule.getName(), rule);
	}
	
	public static CollationComparisonRule forName(String strName) {
		return _map.get(strName);
	}
	
	CollationComparisonRuleKey(String strName, CollationComparisonBasis basis, int flags) {
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(basis, "basis");
		_name = strName;
		_basis = basis;
		_flags = flags;
	}
	
	CollationComparisonRuleKey(String strName, CollationComparisonBasis basis) {
		this(strName, basis, 0);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public CollationComparisonBasis basis() {
		return _basis;
	}

	@Override
	public int flags() {
		return _flags;
	}

	@Override
	public boolean isBinary() {
		return _basis == CollationComparisonBasis.BINARY;
	}

	@Override
	public boolean isCodePointBinary() {
		return _basis == CollationComparisonBasis.CODE_POINT;
	}

	@Override
	public boolean usesCodePage() {
		return _basis == CollationComparisonBasis.CODE_PAGE;
	}

	@Override
	public boolean usesCollator() {
		return _basis == CollationComparisonBasis.COLLATOR;
	}

	@Override
	public boolean isAccentSensitive() {
		return BitMethods.test(_flags, FLAGS_ACCENT);
	}

	@Override
	public boolean isCaseSensitive() {
		return BitMethods.test(_flags, FLAGS_CASE);
	}

	@Override
	public boolean isIdentical() {
		return BitMethods.test(_flags, FLAGS_IDENTICAL);
	}

	@Override
	public boolean isSQLServerLegacy() {
		return BitMethods.test(_flags, FLAGS_SQL);
	}

	@Override
	public boolean isEqual(CollationComparisonRule obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (_basis.equals(obj.basis())
				&& _flags == obj.flags()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int[] members = new int[] { _basis.hashCode(), Integer.valueOf(_flags) };
		return Arrays.hashCode(members);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CollationComparisonRule other) {
			return isEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "collationRule[" + _name + "]";
	}
}
