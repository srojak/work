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
import java.util.Objects;

import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsWriterMethods;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.writers.ObsWriterTextMethods;

/**
 * @author Stephen
 *
 */
public class CodeObsInstance 
		implements CodeObservation, CodeObservationWriting {
	protected final ObsLevel _level;
	protected final SourceLocation _location;
	protected final FlagsInt _flags;
	protected Class<?> _classOrigin;
	protected StringBuilder _sbContent;
	
	protected static final int FLAGS_SHOW_FULL_CLASS_NAMES = 0x1;
	
	public CodeObsInstance(ObsLevel level, SourceLocation location) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(location, "location");
		_level = level;
		_location = location;
		_flags = new FlagsInt();
		_classOrigin = null;
		_sbContent = new StringBuilder();
	}
	
	public void setOriginClass(Class<?> classOrigin) {
		Objects.requireNonNull(classOrigin, "classOrigin");
		_classOrigin = classOrigin;
	}
	
	public void setShowFullClassNames(boolean bState) {
		_flags.apply(bState, FLAGS_SHOW_FULL_CLASS_NAMES);
	}
	
	@Override
	public CodeObservationWriting appendContent(String strText) {
		Objects.requireNonNull(strText, "strText");
		_sbContent.append(strText);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(Object obj) {
		if (obj == null) {
			_sbContent.append(ObsWriterTextMethods.STR_NULL);
		} else {
			_sbContent.append(obj);
		}
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(int value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(boolean value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(char value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(long value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(float value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(double value) {
		_sbContent.append(value);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(StringBuffer sbuf) {
		Objects.requireNonNull(sbuf, "sbuf");
		_sbContent.append(sbuf);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(CharSequence cs) {
		Objects.requireNonNull(cs, "cs");
		_sbContent.append(cs);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(CharSequence cs, int start, int end) {
		Objects.requireNonNull(cs, "cs");
		_sbContent.append(cs, start, end);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(char[] str) {
		Objects.requireNonNull(str, "str");
		_sbContent.append(str);
		return this;
	}

	@Override
	public CodeObservationWriting appendContent(char[] str, int offset, int len) {
		Objects.requireNonNull(str, "str");
		_sbContent.append(str, offset, len);
		return this;
	}

	@Override
	public CodeObservationWriting formatContent(String format, Object... args) {
		Objects.requireNonNull(format, "format");
		SourceLocation loc = SourceLocation.caller();
		_sbContent.append(ObsWriterMethods.formatSafely(loc, format, args));
		return this;
	}

	@Override
	public CodeObservationWriting appendValue(String strInitial, CodeValue cdValue) {
		Objects.requireNonNull(strInitial, "strInitial");
		Objects.requireNonNull(cdValue, "cdValue");
		_sbContent.append(strInitial);
		_sbContent.append(cdValue.format());
		return this;
	}

	@Override
	public CodeObservationWriting appendFrom(TextObservation obsText) {
		Objects.requireNonNull(obsText, "obsText");
		obsText.formatToText(_sbContent);
		return this;
	}

	@Override
	public ObsLevel level() {
		return _level;
	}

	@Override
	public SourceLocation origin() {
		return _location;
	}

	@Override
	public FlagsIntTest flags() {
		return _flags;
	}

	@Override
	public boolean writesFullClassNames() {
		return _flags.test(FLAGS_SHOW_FULL_CLASS_NAMES);
	}

	@Override
	public boolean hasOriginClass() {
		return _classOrigin != null;
	}
	
	protected String getClassNameByRule(Class<?> classRef) {
		if (_flags.test(FLAGS_SHOW_FULL_CLASS_NAMES)) {
			return classRef.getName();
		} else {
			return classRef.getSimpleName();
		}
	}

	@Override
	public String getOriginClassName() {
		return getClassNameByRule(_classOrigin);
	}

	@Override
	public String content() {
		return _sbContent.toString();
	}
	
	protected void innerWriteOrigin(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		if (optionsFormat.contains(ObsFormatOption.CanShowSourceLocations)) {
			if (optionsFormat.contains(ObsFormatOption.ShowFullSourceLocations)) {
				sb.append(' ');
				sb.append(_location.toString(SourceDetail.CLASS_METHOD_LINE));
			} else if (_level.isLevelAtLeast(ObsLevel.TRACE)) { 
				sb.append(' ');
				sb.append(_location.toString(SourceDetail.CLASS_METHOD));
			}
		}
	}
	
	protected void innerWriteOriginClass(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		if (_classOrigin != null) {
			sb.append(" originator=");
			sb.append(getOriginClassName());
		}
	}

	@Override
	public void writeOriginTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		Objects.requireNonNull(sb, "sb");
		Objects.requireNonNull(optionsFormat, "optionsFormat");
		innerWriteOrigin(sb, optionsFormat);
	}

	@Override
	public void writeSelfTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		Objects.requireNonNull(sb, "sb");
		Objects.requireNonNull(optionsFormat, "optionsFormat");
		sb.append(_level.getName());
		innerWriteOrigin(sb, optionsFormat);
		innerWriteOriginClass(sb, optionsFormat);
		if (_sbContent.length() > 0) {
			sb.append(": ");
			sb.append(_sbContent.toString());
		}
	}
	
	public void releaseContent() {
		_sbContent.delete(0, _sbContent.length());
	}
}
