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
package srojak.core.observe;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterLevelFilterBase
		extends ObservationWriterBase
		implements ObservationWriter, HasObsLevel {
	private ObsLevel _levelWriter;
	
	public ObservationWriterLevelFilterBase() {
		_levelWriter = ObsLevel.NOTICE;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return isObsLevelAtLeast(level);
	}
	
	@Override
	public ObsLevel getObsLevel() {
		return _levelWriter;
	}
	
	protected boolean isObsLevelAtLeast(ObsLevel obsEvent) {
		Objects.requireNonNull(obsEvent, "obsEvent");
		return _levelWriter.isLevelAtLeast(obsEvent);
	}
	
	@Override
	public void setObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_levelWriter = level;
	}
	
	@Override
	public abstract void write(ObsLevel level, String strText);
	
	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		write(collector.getLevel(), strText);
	}
}
