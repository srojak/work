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
package srojak.sql.mapobj.props;

import java.lang.reflect.Field;
import java.util.Comparator;

import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableComparable;
import srojak.numerics.NullableValue;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedComparableProperty 
		extends AbstractMappedObjectProperty {

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 * @param sqlType
	 */
	public AbstractMappedComparableProperty(Field field, String strColumnName, FlagsIntTest flagsSource, int sqlType) {
		super(field, strColumnName, flagsSource, sqlType);
	}
	
	protected abstract NullableComparable<?> createComparableDefault();

	@Override
	protected NullableValue createTypedDefault() {
		return createComparableDefault();
	}

	@Override
	public Comparator<?> getComparator() {
		NullableComparable<?> nble = createComparableDefault();
		return nble.getComparator();
	}

}
