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
package srojak.sql.pstmt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObservationCollectorCore;
import srojak.sql.DatabaseAction;
import srojak.sql.DatabaseObject;
import srojak.sql.MappedObjectException;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public class PrepInsertStmtWorkSession 
		extends AbstractPrepStmtWorkSession {
	private final List<String> _listColumns;
	private final DatabaseObject _dbobj;

	/**
	 * 
	 */
	PrepInsertStmtWorkSession(ObservationCollectorCore collector, int flags, DatabaseObject dbObject) {
		super(collector, flags);
		Objects.requireNonNull(dbObject, "dbObject");
		_listColumns = new LinkedList<String>();
		_dbobj = dbObject;
	}
	
	public <M extends AbstractMappedObject> void bindTo(M source) 
			throws ValueBindingException, MappedObjectException {
		Objects.requireNonNull(source, "source");
		List<JdbcValueBinding> listBindings = source.bindPropertyValues(DatabaseAction.INSERT);
		for (JdbcValueBinding binding : listBindings) {
			addAndNumber(binding);
			_listColumns.add(binding.getName());
		}
	}

	@Override
	protected String createStatement() {
		if (_listColumns.isEmpty()) {
			throw new IllegalStateException("no columns");
		}
		if (_listColumns.size() != _listValueSources.size()) {
			throw new IllegalStateException("mismatch between column names and sources");
		}
		StringBuilder sbStmt = new StringBuilder("INSERT INTO ");
		sbStmt.append(_dbobj.fullName());
		sbStmt.append("\n    ( ");
		StringBuilder sbValues = new StringBuilder();
		
		Iterator<String> iterNames = _listColumns.iterator();
		String strColumn = iterNames.next();
		sbStmt.append(strColumn);
		sbValues.append("?");
		while (iterNames.hasNext()) {
			strColumn = iterNames.next();
			sbStmt.append(", ");
			sbStmt.append(strColumn);
			sbValues.append(", ?");
		}
		sbStmt.append(")\n VALUES ( ");
		sbStmt.append(sbValues.toString());
		sbStmt.append(" )");
		return sbStmt.toString();
	}
}
