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
package srojak.core.specialized;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Stephen
 *
 */
public class SysTimeStamp
		implements Serializable {
	private final Instant _now;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4827331611764275693L;
	
	public SysTimeStamp() {
		_now = Instant.now();
	}
	
	public LocalDateTime getLocalDateTime() {
		return LocalDateTime.ofInstant(_now, ZoneId.systemDefault());
	}
}
