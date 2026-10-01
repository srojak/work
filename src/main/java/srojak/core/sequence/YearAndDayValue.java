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
package srojak.core.sequence;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class YearAndDayValue 
		implements YearAndDay {
	private int _year;
	private int _julian;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -911339589044353858L;
	
	public YearAndDayValue(int year, int yday) {
		_year = year;
		_julian = yday;
	}
	
	public YearAndDayValue(LocalDate date) {
		Objects.requireNonNull(date, "date");
		_year = date.getYear();
		_julian = date.getDayOfYear();
	}

	@Override
	public int getYear() {
		return _year;
	}

	@Override
	public int getDayOfYear() {
		return _julian;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_year, _julian);
	}
	
	private boolean coreEqual(YearAndDay other) {
		return _year == other.getYear() && _julian == other.getDayOfYear();
	}

	@Override
	public boolean isEqual(YearAndDay obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			return coreEqual(obj);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof YearAndDay other) {
			return coreEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(YearAndDay o) {
		if (o == null) {
			return 1;
		} else {
			int nc = Integer.compare(_year,  o.getYear());
			if (nc == 0) {
				nc = Integer.compare(_julian, o.getDayOfYear());
			}
			return nc;
		}
	}

	@Override
	public String toString() {
		return String.format("%04d-%0d3", _year, _julian);
	}
}
