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
package srojak.cdo;

import java.awt.Dimension;
import java.util.Objects;

import srojak.cdo.events.ScaleChangeEvent;
import srojak.cdo.events.ScaleChangeListener;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.numerics.DoubleMethods;
import srojak.numerics.IntervalType;
import srojak.numerics.OrderedComparison;
import srojak.numerics.intervals.IntervalDouble;

/**
 * @author Stephen
 *
 */
public class ScaleControl
		implements Scaler {
	private final SingleEventListenerStore<ScaleChangeListener> _listeners;
	private final Dimension _dmSurface;
	private double _dScale;
	private IntervalDouble _dintvRange;
	private Dimension _dmScaledSurface;
	
	public ScaleControl(double dOriginalScale, Dimension dmSurface) {
		Objects.requireNonNull(dmSurface, "dmSurface");
		if (DoubleMethods.compare(OrderedComparison.LE, dOriginalScale, 0.0d)) {
			throw new IllegalArgumentException("dOriginalScale must be positive");
		}
		
		_listeners = new SingleEventListenerList<ScaleChangeListener>();
		_dScale = dOriginalScale;
		_dmSurface = dmSurface;
		_dmScaledSurface = GraphicsUnits.scale(_dmSurface, _dScale);
		_dintvRange = null;
	}
	
	protected void setLimits(double dMinimum, double dMaximum, boolean bNotifyChanges) {
		_dintvRange = new IntervalDouble(IntervalType.CLOSED, dMinimum, dMaximum);
		int nCompar = _dintvRange.compareToInterval(_dScale);
		switch (Integer.signum(nCompar)) {
		case 0:
			return;
			
		case -1:
			_dScale = _dintvRange.getMinimum();		
			break;
			
		case 1:
			_dScale = _dintvRange.getMaximum();
			break;
		}
		if (bNotifyChanges) {
			ScaleChangeEvent event = new ScaleChangeEvent(this, _dScale);
			_listeners.forEach(ls -> ls.scaleChanged(event));
		}
	}

	@Override
	public double getScale() {
		return _dScale;
	}

	@Override
	public Dimension getUnscaledSurfaceSize() {
		return _dmSurface;
	}

	@Override
	public Dimension getScaledFullSize() {
		return _dmScaledSurface;
	}

	@Override
	public boolean isRangeLimited() {
		return _dintvRange != null;
	}

	@Override
	public IntervalDouble getLimits() {
		return _dintvRange;
	}

	@Override
	public void addScaleChangeListener(ScaleChangeListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeScaleChangeListener(ScaleChangeListener listener) {
		_listeners.remove(listener);
	}
	
	protected double applyLimits(double dNewScale) {
		int nCompar = _dintvRange.compareToInterval(dNewScale);
		switch (Integer.signum(nCompar)) {
			
		case -1:
			dNewScale = _dintvRange.getMinimum();
			break;
			
		case 1:
			dNewScale = _dintvRange.getMaximum();
			break;
		}
		return dNewScale;
	}
	
	public final void setScale(double dNewScale) {
		if (DoubleMethods.compare(OrderedComparison.LE, dNewScale, 0.0d)) {
			throw new IllegalArgumentException("dNewScale must be positive");
		}
		if (_dintvRange != null) {
			dNewScale = applyLimits(dNewScale);
		}
		_dScale = dNewScale;
		_dmScaledSurface = GraphicsUnits.scale(_dmSurface, _dScale);
		ScaleChangeEvent event = new ScaleChangeEvent(this, _dScale);
		_listeners.forEach(ls -> ls.scaleChanged(event));
	}

}
