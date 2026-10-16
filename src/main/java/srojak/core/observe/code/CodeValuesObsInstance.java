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
package srojak.core.observe.code;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class CodeValuesObsInstance 
		extends CodeObsInstance 
		implements CodeValuesObservation, CodeValuesObservationWriting {
	private final List<CodeValue> _listNamedValues;

	/**
	 * @param level
	 * @param location
	 */
	public CodeValuesObsInstance(ObsLevel level, SourceLocation location) {
		super(level, location);
		_listNamedValues = new LinkedList<CodeValue>();
	}

	@Override
	public boolean hasNamedValues() {
		return !_listNamedValues.isEmpty();
	}

	@Override
	public Stream<CodeValue> getNamedValues() {
		return _listNamedValues.stream();
	}

	@Override
	public void addNamedValue(CodeValue value) {
		Objects.requireNonNull(value, "value");
		_listNamedValues.add(value);
	}

	@Override
	public void writeSelfTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		super.writeSelfTo(sb, optionsFormat);
		if (!_listNamedValues.isEmpty()) {
			sb.append("\nnamed values:");
			_listNamedValues.forEach(nv -> {
				sb.append("\n    ");
				sb.append(nv.format());
			});
		}
	}

	@Override
	public void releaseContent() {
		super.releaseContent();
		_listNamedValues.clear();
	}
}
