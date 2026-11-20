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

import java.util.stream.Stream;

import srojak.core.logic.FlagsIntTest;

/**
 * @author Stephen
 *
 */
public interface MappedObjectSource {

	FlagsIntTest flags();
	boolean isSingleTable();
	boolean canInsert();
	boolean canUpdate();
	boolean canDelete();
	String[] columnNames();
	String stmtInsert();
	String stmtRead();
	String stmtUpdate();
	String stmtDelete();
	Stream<MappedObjectProperty> properties();
	MappedObjectProperty propertyForColumn(String strColumnName);	
	
	static final int FLAGS_SINGLE = 0x1;
	static final int FLAGS_CAN_INSERT = 0x2;
	static final int FLAGS_CAN_UPDATE = 0x4;
	static final int FLAGS_CAN_DELETE = 0x8;
}
