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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectorCore;
import srojak.core.tools.BitMethods;
import srojak.numerics.sequence.IntSequenceSimple;
import srojak.sql.JdbcValueSource;
import srojak.sql.ValueBindingException;

/**
 * @author Stephen
 *
 */
public abstract class AbstractPrepStmtWorkSession
		implements AbstractPrepStmtFlags {
	protected final List<NumberedJdbcValueSource> _listValueSources;
	protected final ObservationCollectorCore _collector;
	protected final int _flags;
	private final IntSequenceSimple _sequence;

	/**
	 * 
	 */
	public AbstractPrepStmtWorkSession(ObservationCollectorCore collector, int flags) {
		Objects.requireNonNull(collector, "collector");
		_listValueSources = new LinkedList<NumberedJdbcValueSource>();
		_collector = collector;
		_flags = flags;
		_sequence = new IntSequenceSimple(0);
	}

	protected int addAndNumber(JdbcValueSource source) {
		Objects.requireNonNull(source, "source");
		NumberedJdbcValueSource ns = new NumberedJdbcValueSource(source, _sequence.next());
		_listValueSources.add(ns);
		return ns.ordinal();
	}
	
	protected abstract String createStatement();
	
	public PreparedStatement createStatement(Connection connect)
			throws SQLException, ValueBindingException {
		Objects.requireNonNull(connect, "connect");
		final String text = createStatement();
		if (BitMethods.test(_flags, PS_FLAGS_SHOW_SQL)) {
			_collector.record(ObsLevel.DEBUG, () -> "statement: " + text);
		}
		PreparedStatement stmt = connect.prepareStatement(text);
		Iterator<NumberedJdbcValueSource> iterator = _listValueSources.iterator();
		while (iterator.hasNext()) {
			NumberedJdbcValueSource source = iterator.next();
			source.applyTo(stmt);
		}
		return stmt;
	}
}
