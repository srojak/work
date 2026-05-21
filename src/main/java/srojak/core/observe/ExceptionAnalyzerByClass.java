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

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ExceptionAnalyzerByClass
		extends ExceptionAnalyzerCommonBase {
	private final HashMap<String, ExceptionRenderer> _map;
	private boolean _bShowStack;

	/**
	 * @param writer
	 */
	public ExceptionAnalyzerByClass(ObservationWriter writer) {
		super(writer);
		_map = new HashMap<String, ExceptionRenderer>();
		_bShowStack = false;
	}
	
	public boolean showStack() {
		return _bShowStack;
	}
	
	public void setShowStack(boolean bState) {
		_bShowStack = bState;
	}
	
	public final void define(Class<? extends Exception> classExc, ExceptionRenderer renderer) {
		Objects.requireNonNull(classExc, "classExc");
		Objects.requireNonNull(renderer, "renderer");
		_map.put(classExc.getTypeName(), renderer);
	}
	
	protected void defaultRenderer(ObservationWriter writer, ObsLevel level, SourceLocation location,
			Exception exception) {
		writer.buildAndWrite(level, sb -> {
			sb.append("caught ");
			sb.append(exception.getClass().getSimpleName());
			sb.append(" at ");
			sb.append(location);
			sb.append("\n    ");
			sb.append(exception.getMessage());
		});
	}

	@Override
	public final void analyze(ObsLevel level, SourceLocation location, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(location, "location");
		Objects.requireNonNull(exc, "exc");
		ExceptionRenderer renderer = _map.get(exc.getClass().getTypeName());
		if (renderer == null) {
			defaultRenderer(_writer, level, location, exc);
		} else {
			renderer.render(_writer, level, location, exc);
		}
		if (_bShowStack) {
			StringBuilder sb = new StringBuilder("stack trace:");
			StackTraceElement[] frames = exc.getStackTrace();
			for (StackTraceElement frame : frames) {
				sb.append("\n    ");
				sb.append(frame);
			}
			_writer.write(level, sb.toString());
		}
	}
}
