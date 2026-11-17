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
package srojak.sql.mapobj.factories;

import java.lang.reflect.Field;

import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsIntTest;
import srojak.core.logic.FlagsShortTest;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.ColumnTimeZone;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.MappedObjectPropertyFlags;

/**
 * @author Stephen
 *
 */
public interface MappedObjectPropertyFactory
		extends MappedObjectPropertyFlags {

	ClassKey forClass();
	boolean isTextField();
	boolean isTimeZonedField();
	FlagsShortTest flagsDefining();
	boolean isInKey();
	FlagsIntTest flagsWorking();
	void reset();
	void setField(Field field);
	void apply(ClassKey keyForObject, ColumnSource annotColumn) 
			throws MappedObjectException;
	void apply(ClassKey keyForObject, KeyColumn annotKey) 
			throws MappedObjectException;
	void apply(ClassKey keyForObject, ColumnTimeZone annotZone) 
			throws MappedObjectException;
	MappedObjectProperty create(ClassKey keyForObject) 
			throws MappedObjectException;
	
	static final short FLAGS_DEFINE_IS_TEXT = 0x1;
	static final short FLAGS_DEFINE_IS_TIME_ZONED = 0x2;	
}
