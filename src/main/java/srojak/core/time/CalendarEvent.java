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
package srojak.core.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import srojak.core.GlobalFormats;
import srojak.core.events.CoreEvent;

/**
 * @author Stephen
 *
 */
public class CalendarEvent 
		extends CoreEvent {
	private final LocalDate _date;

	/**
	 * @param source
	 */
	public CalendarEvent(Object source, LocalDate date) {
		super(source);
		Objects.requireNonNull(date, "date");
		_date = date;
	}
	
	public LocalDate getDate() {
		return _date;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", date=");
		sb.append(_date.format(DateTimeFormatter.ISO_DATE));
	}

}
