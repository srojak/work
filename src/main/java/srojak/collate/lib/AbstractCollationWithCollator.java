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
package srojak.collate.lib;

import java.text.CollationElementIterator;
import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Objects;

import srojak.collate.CollationDefinition;
import srojak.collate.CollationUsingCollator;

/**
 * @author Stephen
 *
 */
public abstract class AbstractCollationWithCollator 
		extends AbstractCollation
		implements CollationUsingCollator {
	protected final Collator _collator;

	/**
	 * @param definition
	 */
	public AbstractCollationWithCollator(CollationDefinition definition, Collator collator) {
		super(definition);
		Objects.requireNonNull(collator, "collator");
		_collator = collator;
	}

	@Override
	public Collator getCollator() {
		return _collator;
	}

	@Override
	public boolean isRuleBasedCollator() {
		return _collator instanceof RuleBasedCollator;
	}

	@Override
	public CollationKey getCollationKey(String source) {
		return _collator.getCollationKey(source);
	}
	
	private boolean compareCollationElements(int e1, int e2) {
		if (_collator.getStrength() == Collator.IDENTICAL) {
			return e1 == e2;
		}
		if (CollationElementIterator.primaryOrder(e1) != CollationElementIterator.primaryOrder(e2)) {
			return false;
		}
		if (_collator.getStrength() == Collator.PRIMARY) {
			return true;
		}
		if (CollationElementIterator.secondaryOrder(e1) != CollationElementIterator.secondaryOrder(e2)) {
			return false;
		}
		if (_collator.getStrength() == Collator.SECONDARY) {
			return true;
		}
		return CollationElementIterator.tertiaryOrder(e1) != CollationElementIterator.tertiaryOrder(e2);
	}

	@Override
	protected int innerCompare(String source, String target) {
		return _collator.compare(source, target);
	}

	@Override
	protected int innerIndexOf(String source, String find, int fromIndex) {
		if (_collator instanceof RuleBasedCollator rbc) {
			CollationElementIterator iterSource = rbc.getCollationElementIterator(source);
			CollationElementIterator iterFind = rbc.getCollationElementIterator(find);
			while (iterSource.getOffset() < fromIndex) {
				iterSource.next();
			}
			while (true) {
				int eFind = iterFind.next();
				if (eFind == CollationElementIterator.NULLORDER) {
					return iterSource.getOffset();
				}
				int eSource = iterSource.next();
				if (eSource == CollationElementIterator.NULLORDER) {
					return -1;
				}
				if (!compareCollationElements(eSource, eFind)) {
					return -1;
				}
			}
		} else {
			// fall back on code points
			return indexByCodePoint(source, find, fromIndex);
		}
	}

}
