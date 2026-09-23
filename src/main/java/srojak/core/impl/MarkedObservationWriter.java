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
package srojak.core.impl;

import java.util.Objects;

import srojak.core.Wrapped;
import srojak.core.observe.ObservationWriter;

/**
 * @author Stephen
 *
 */
public class MarkedObservationWriter 
		implements Wrapped<ObservationWriter> {
	private final ObservationWriter _writer;
	private final boolean _isPermanent;
	
	public MarkedObservationWriter(ObservationWriter writer, boolean bIsPermanent) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
		_isPermanent = bIsPermanent;
	}
	
	public boolean isPermanent() {
		return _isPermanent;
	}

	@Override
	public Object getWrappedObject() {
		return _writer;
	}

	@Override
	public ObservationWriter getWrapped() {
		return _writer;
	}

}
