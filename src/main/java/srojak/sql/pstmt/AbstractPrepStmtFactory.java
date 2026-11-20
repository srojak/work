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

import java.util.Objects;

import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.HasReplaceableObservationCollector;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCollectorCore;

/**
 * @author Stephen
 *
 */
public class AbstractPrepStmtFactory 
		implements HasReplaceableObservationCollector, AbstractPrepStmtFlags {
	protected final FlagsInt _flags;
	private ObservationCollectorCore _collector;

	/**
	 * 
	 */
	public AbstractPrepStmtFactory() {
		_flags = new FlagsInt();
		_collector = ObservationCollector.create();
	}

	@Override
	public ObservationCollectorCore getObservationCollector() {
		return _collector;
	}

	@Override
	public void setObservationCollector(ObservationCollectorCore collector) {
		Objects.requireNonNull(collector, "collector");
		_collector = collector;
	}

	public final FlagsIntTest getFlags() {
		return _flags;
	}
	
	public boolean isShowSQLOn() {
		return _flags.test(PS_FLAGS_SHOW_SQL);
	}
	
	public void setShowSQLOn(boolean bState) {
		_flags.apply(bState, PS_FLAGS_SHOW_SQL);
	}
}
