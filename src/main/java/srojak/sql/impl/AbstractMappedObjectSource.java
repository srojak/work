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
package srojak.sql.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.excepts.InvalidOperationException;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.tools.ListMethods;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.MappedObjectSource;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedObjectSource
		implements MappedObjectSource {
	protected final FlagsInt _flags;
	protected final List<MappedObjectProperty> _listColumns;

	protected AbstractMappedObjectSource() {
		_flags = new FlagsInt();
		_listColumns = new LinkedList<MappedObjectProperty>();
	}

	@Override
	public FlagsIntTest flags() {
		return _flags;
	}

	@Override
	public boolean isSingleTable() {
		return _flags.test(FLAGS_SINGLE);
	}

	@Override
	public boolean canInsert() {
		return _flags.test(FLAGS_CAN_INSERT);
	}

	@Override
	public boolean canUpdate() {
		return _flags.test(FLAGS_CAN_UPDATE);
	}

	@Override
	public boolean canDelete() {
		return _flags.test(FLAGS_CAN_DELETE);
	}

	@Override
	public String[] columnNames() {
		return _listColumns.stream().map(c -> c.columnName()).toArray(n -> new String[n]);
	}

	@Override
	public String stmtInsert() {
		throw new InvalidOperationException("Insert", "not defined");
	}

	@Override
	public String stmtUpdate() {
		throw new InvalidOperationException("Update", "not defined");
	}

	@Override
	public String stmtDelete() {
		throw new InvalidOperationException("Delete", "not defined");
	}
	
	@Override
	public Stream<MappedObjectProperty> properties() {
		return _listColumns.stream();
	}

	@Override
	public MappedObjectProperty propertyForColumn(String strColumnName) {
		return ListMethods.findInList(_listColumns, c -> c.columnNameEquals(strColumnName));
	}

	public void add(MappedObjectProperty column) {
		Objects.requireNonNull(column, "column");
		_listColumns.add(column);
	}
}
