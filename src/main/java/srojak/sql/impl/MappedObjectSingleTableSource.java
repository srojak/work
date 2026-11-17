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

import java.util.Iterator;
import java.util.Objects;

import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.SingleTableSource;

/**
 * @author Stephen
 *
 */
public class MappedObjectSingleTableSource 
		extends AbstractMappedObjectSource {
	private final String _sourceObject;
	private final String _restrict;
	
	public MappedObjectSingleTableSource(SingleTableSource annotSource) {
		super();
		Objects.requireNonNull(annotSource, "annotSource");
		_sourceObject = annotSource.schema() + "." + annotSource.name();
		_restrict = annotSource.restriction();
		if (annotSource.canInsert()) {
			_flags.set(FLAGS_CAN_INSERT);
		}
		if (annotSource.canUpdate()) {
			_flags.set(FLAGS_CAN_UPDATE);
		}
		if (annotSource.canDelete()) {
			_flags.set(FLAGS_CAN_DELETE);
		}
	}

	@Override
	public String stmtRead() {
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		Iterator<MappedObjectProperty> iterColumns = _listColumns.iterator();
		if (iterColumns.hasNext()) {
			MappedObjectProperty property = iterColumns.next();
			sbQuery.append(property.columnName());
		}
		while (iterColumns.hasNext()) {
			MappedObjectProperty property = iterColumns.next();
			sbQuery.append(", ").append(property.columnName());
		}
		sbQuery.append("\n\tFROM ");
		sbQuery.append(_sourceObject);
		if (!_restrict.isBlank()) {
			sbQuery.append("\n\tWHERE ");
			sbQuery.append(_restrict);
		}
		return sbQuery.toString();
	}

}
