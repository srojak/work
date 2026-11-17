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
package srojak.sql.mapobj;

/**
 * @author Stephen
 *
 */
public interface MappedObjectPropertyFlags {
	
	static final int FLAGS_NULLABLE = 0x1;
	static final int FLAGS_READONLY = 0x2;
	static final int FLAGS_IS_IN_KEY = 0x4;
	static final int FLAGS_IDENTITY = 0x8;
	static final int FLAGS_LARGE_DATA = 0x10;
	static final int FLAGS_FIXED_LENGTH = 0x20;
	static final int FLAGS_UNICODE = 0x40;
	static final int FLAGS_TRIM_AFTER_READ = 0x80;
}
