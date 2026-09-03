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
package srojak.core.concurrent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenBearing;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterHolder;
import srojak.core.observe.writers.ObservationWriterPrintStream;

/**
 * @author Stephen
 *
 */
public class StopGate 
		implements NameTokenBearing {
	private final NameToken _name;
	private final List<StopBarrier> _listStops;
	
	private static final ObservationWriterHolder _writerErr;
	
	static {
		_writerErr = new ObservationWriterHolder(new ObservationWriterPrintStream(System.err));
	}
	
	public static ObservationWriter getWriter() {
		return _writerErr.getWriter();
	}
	
	public static void setWriter(ObservationWriter writer) {
		_writerErr.setWriter(_writerErr);
	}
	
	public StopGate(NameToken tokenName) {
		Objects.requireNonNull(tokenName, "tokenName");
		_name = tokenName;
		_listStops = Collections.synchronizedList(new LinkedList<StopBarrier>());
	}
	
	@Override
	public NameToken getNameToken() {
		return _name;
	}
	
	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return _name.equals(token);
	}
	
	public boolean isClear() {
		return _listStops.isEmpty();
	}
	
	public int getStopCount() {
		return _listStops.size();
	}
	
	public List<StopBarrier> getStops() {
		return List.copyOf(_listStops);
	}
	
	public StopBarrier addStop(Object objBarrier) {
		Objects.requireNonNull(objBarrier, "objBarrier");
		StopBarrier barrier = new StopBarrier(this, objBarrier);
		_listStops.add(barrier);
		return barrier;
	}
	
	void removeBarrier(StopBarrier barrier) {
		_listStops.remove(barrier);
	}
	
	void finalRemove(StopBarrier barrier) {
		if (_listStops.remove(barrier)) {
			_writerErr.write(ObsLevel.ERROR, "gate for " + _name + " barrier never properly removed");
		}
	}
}
