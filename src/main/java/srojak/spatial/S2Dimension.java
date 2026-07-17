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
package srojak.spatial;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class S2Dimension {
	public final int width;
	public final int height;
	
	public S2Dimension(int nWidth, int nHeight) {
		if (nWidth <= 0) {
			throw new IllegalArgumentException("nWidth must be positive");
		}
		if (nHeight <= 0) {
			throw new IllegalArgumentException("nHeight must be positive");
		}
		width = nWidth;
		height = nHeight;
	}
	
	public int getArea() {
		return width * height;
	}
	
	public int getPerimeter() {
		return 2 * (width + height);
	}

	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof S2FieldSize other) {
			return width == other.width && height == other.height;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("size(");
		sb.append(width);
		sb.append(", ");
		sb.append(height);
		sb.append(')');
		return sb.toString();
	}
}
