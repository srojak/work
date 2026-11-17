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
package srojak.sql.collate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.collate.CollationCatalog;
import srojak.collate.CollationDefinition;
import srojak.collate.DecoratedCollation;
import srojak.collate.cmprule.CollationComparisonRuleKey;
import srojak.core.NameToken;
import srojak.core.excepts.KeyNotFoundException;
import srojak.mantle.decorated.DecoratorIntValue;
import srojak.sql.SQLCollation;

/**
 * @author Stephen
 *
 * @see https://learn.microsoft.com/en-us/sql/relational-databases/collations/collation-and-unicode-support
 */
public class SQLCollationCatalog {

	private static final Map<SQLCollation, DecoratedCollation> _map
			= new HashMap<SQLCollation, DecoratedCollation>();
	// I don't want static initialization because of the mess it makes
	// if it throws an exception.
	
	public static final NameToken DELTA = NameToken.factory("delta");
	
	public static void setDistance(DecoratedCollation dc, int factorDelta) {
		Objects.requireNonNull(dc, "dc");
		if (factorDelta < 0) {
			throw new IllegalArgumentException("factorDelta cannot be negative");
		}
		dc.putDecorator(new DecoratorIntValue(DELTA, factorDelta));
	}
	
	public static synchronized DecoratedCollation get(SQLCollation sqlColl) {
		initialize();
		return _map.get(sqlColl);
	}
	
	public static synchronized void define(SQLCollation sqlColl, CollationDefinition definition, int factorDelta) {
		Objects.requireNonNull(sqlColl, "sqlColl");
		Objects.requireNonNull(definition, "definition");
		Collation collation = CollationCatalog.get(definition);
		if (collation == null) {
			throw new KeyNotFoundException(definition, "collation not defined");
		}
		DecoratedCollation dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, factorDelta);
		_map.put(sqlColl, dc);
	}
	
	public static synchronized void initialize() {
		if (!_map.isEmpty()) {
			return;
		}
		CollationCatalog.loadInitial();
		
		Collation collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.BIN));
		_map.put(SQLCollation.name("Latin1_General_BIN"), DecoratedCollation.copyFrom(collation));
		DecoratedCollation dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_BIN"), dc);
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CDPT));
		dc = DecoratedCollation.copyFrom(collation);
		_map.put(SQLCollation.name("Latin1_General_BIN2"), DecoratedCollation.copyFrom(collation));
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_BIN2"), dc);
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AI));
		_map.put(SQLCollation.name("Latin1_General_CI_AI"), DecoratedCollation.copyFrom(collation));
		_map.put(SQLCollation.name("Latin1_General_CI_AI_WS"), DecoratedCollation.copyFrom(collation));
		_map.put(SQLCollation.name("Latin1_General_CI_AI_KS_WS"), DecoratedCollation.copyFrom(collation));
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CI_AI"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CI_AI_WS"), dc);
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AS));
		_map.put(SQLCollation.name("Latin1_General_CI_AS"), DecoratedCollation.copyFrom(collation));
		_map.put(SQLCollation.name("Latin1_General_CI_AS_WS"), DecoratedCollation.copyFrom(collation));
		// see sys.objects.type
		_map.put(SQLCollation.name("Latin1_General_CI_AS_KS_WS"), DecoratedCollation.copyFrom(collation));
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CI_AS"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CI_AS_WS"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CI_AS_KS_WS"), dc);
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CS_AS));
		_map.put(SQLCollation.name("Latin1_General_CS_AS"), DecoratedCollation.copyFrom(collation));
		_map.put(SQLCollation.name("Latin1_General_CS_AS_WS"), DecoratedCollation.copyFrom(collation));
		_map.put(SQLCollation.name("Latin1_General_CS_AS_KS_WS"), DecoratedCollation.copyFrom(collation));
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AS_SC"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AS_WS_SC"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AS_KS_WS_SC"), dc);
		// there is no collator that implements CS_AI
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 5);
		_map.put(SQLCollation.name("Latin1_General_CS_AI"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 5);
		_map.put(SQLCollation.name("Latin1_General_CS_AI_WS"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 5);
		_map.put(SQLCollation.name("Latin1_General_CS_AI_KS_WS"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 6);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AI_SC"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AI_WS_SC"), dc);
		dc = DecoratedCollation.copyFrom(collation);
		setDistance(dc, 1);
		_map.put(SQLCollation.name("Latin1_General_100_CS_AI_KS_WS_SC"), dc);
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CI_AI));
		_map.put(SQLCollation.name("SQL_Latin1_General_CP1_CI_AI"), DecoratedCollation.copyFrom(collation));
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CI_AS));
		_map.put(SQLCollation.name("SQL_Latin1_General_CP1_CI_AS"), DecoratedCollation.copyFrom(collation));
		
		collation = CollationCatalog.get(CollationDefinition.compose(Locale.ENGLISH,
				StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CS_AS));
		_map.put(SQLCollation.name("SQL_Latin1_General_CP1_CS_AS"), DecoratedCollation.copyFrom(collation));
	}
}
