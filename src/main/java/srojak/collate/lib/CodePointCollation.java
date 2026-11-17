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

import java.util.Objects;

import srojak.collate.CollationDefinition;

/**
 * @author Stephen
 *
 */
public class CodePointCollation 
		extends AbstractCollation {

	/**
	 * @param definition
	 */
	public CodePointCollation(CollationDefinition definition) {
		super(definition);
	}

	@Override
	protected int innerCompare(String source, String target) {
		return compareByCodePoints(source, target);
	}

	@Override
	protected int innerIndexOf(String source, String find, int fromIndex) {
		return indexByCodePoint(source, find, fromIndex);
	}

	@Override
	public boolean regionMatches(String source, int toffset, String other, int ooffset, int len) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(other, "other");
		String subSource = source.substring(toffset, toffset+len);
		String subOther = other.substring(ooffset, ooffset+len);
		return compareByCodePoints(subSource, subOther) == 0;
	}
}
