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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import srojak.collate.cmprule.CollationComparisonRuleKey;
import srojak.collate.lib.BinaryCollation;
import srojak.collate.lib.CodePointCollation;
import srojak.collate.lib.CommonCollation;
import srojak.collate.lib.SqlCodePageCollation;
import srojak.core.excepts.DuplicateKeyException;

/**
 * @author Stephen
 *
 */
public class CollationCatalog {
	private static final Map<CollationDefinition, Collation> _map
		= new HashMap<CollationDefinition, Collation>();
	
	public static synchronized Collation get(CollationDefinition definition) {
		Objects.requireNonNull(definition, "definition");
		return _map.get(definition);
	}
	
	public static synchronized void define(Collation collation) {
		Objects.requireNonNull(collation, "collation");
		if (_map.putIfAbsent(collation.key(), collation) != null) {
			throw new DuplicateKeyException(collation.key(), "collation already defined");
		}
	}
	
	public static synchronized Set<CollationDefinition> copyKeys() {
		return new HashSet<CollationDefinition>(_map.keySet());
	}
	
	public static void loadInitial() {
		// only do this once
		if (_map.isEmpty()) {
			CollationDefinition definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.BIN);
			_map.put(definition, new BinaryCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CDPT);
			_map.put(definition, new CodePointCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AI);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AS);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CS_AS);
			_map.put(definition, new CommonCollation(definition));
			
			Locale lclES = Locale.forLanguageTag("es");
			definition = CollationDefinition.compose(lclES,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.BIN);
			_map.put(definition, new BinaryCollation(definition));
			
			definition = CollationDefinition.compose(lclES,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CDPT);
			_map.put(definition, new CodePointCollation(definition));
			
			definition = CollationDefinition.compose(lclES,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AI);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(lclES,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AS);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(lclES,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CS_AS);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(Locale.FRENCH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.BIN);
			_map.put(definition, new BinaryCollation(definition));
			
			definition = CollationDefinition.compose(Locale.FRENCH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CDPT);
			_map.put(definition, new CodePointCollation(definition));
			
			definition = CollationDefinition.compose(Locale.FRENCH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AI);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(Locale.FRENCH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CI_AS);
			_map.put(definition, new CommonCollation(definition));
			
			definition = CollationDefinition.compose(Locale.FRENCH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.CS_AS);
			_map.put(definition, new CommonCollation(definition));
				
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CI_AI);
			_map.put(definition, new SqlCodePageCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CI_AS);
			_map.put(definition, new SqlCodePageCollation(definition));
			
			definition = CollationDefinition.compose(Locale.ENGLISH,
					StandardCharsets.ISO_8859_1, CollationComparisonRuleKey.SQL_CPG_CS_AS);
			_map.put(definition, new SqlCodePageCollation(definition));
		}
	}
}
