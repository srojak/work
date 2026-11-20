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
package srojak.sql;

import java.util.Comparator;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.collate.HasCollation;
import srojak.numerics.nullable.AbstractNullableComparator;

/**
 * @author Stephen
 *
 */
public class NullableCollatedStringComparator 
		extends AbstractNullableComparator<NullableCollatedString>
		implements Comparator<NullableCollatedString>, HasCollation {
	protected final Collation _collation;

	/**
	 * @param collation
	 */
	public NullableCollatedStringComparator(Collation collation) {
		super();
		Objects.requireNonNull(collation, "collation");
		_collation = collation;
	}

	@Override
	public Collation getCollation() {
		return _collation;
	}

	@Override
	protected int compareNonNull(NullableCollatedString operand1, NullableCollatedString operand2) {
		return _collation.compare(operand1.value(), operand2.value());
	}
}
