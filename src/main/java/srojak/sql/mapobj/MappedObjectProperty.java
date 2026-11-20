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
package srojak.sql.mapobj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import srojak.core.keys.InterfaceKey;
import srojak.core.logic.FlagsIntTest;
import srojak.sql.ValueBindingException;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public interface MappedObjectProperty
		extends MappedObjectPropertyFlags {

	InterfaceKey fieldType();
	String fieldName();
	Comparator<?> getComparator();
	String columnName();
	boolean columnNameEquals(String strName);
	FlagsIntTest getFlags();
	boolean isKeyComponent();
	int keyOrdinal();
	boolean isNullable();
	boolean isReadOnly();
	boolean isIdentity();
	int sqlType();
	void setToDefault(AbstractMappedObject instance) 
			throws IllegalAccessException;
	void readIntoProperty(AbstractMappedObject instance, ResultSet rset)
			throws IllegalArgumentException, IllegalAccessException, SQLException;
	JdbcValueBinding bind(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException, ValueBindingException;
}
