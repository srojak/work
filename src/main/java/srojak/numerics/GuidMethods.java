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
package srojak.numerics;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Stephen
 *
 * A Microsoft Guid has the bytes in a different order.
 * These methods resolve the differences.
 * 
 */
public class GuidMethods {
	
	public static UUID fromGuidBytes(byte[] bytes) {
		Objects.requireNonNull(bytes, "bytes");
		if (bytes.length != 16) {
			throw new IllegalArgumentException("length of bytes must be 16");
		}
		byte[] bytesUpper = new byte[8];
		for (int idx = 0; idx < 4; idx++) {
			bytesUpper[3 - idx] = bytes[idx];
		}
		bytesUpper[4] = bytes[5];
		bytesUpper[5] = bytes[4];
		bytesUpper[6] = bytes[7];
		bytesUpper[7] = bytes[6];
		byte[] bytesLower = Arrays.copyOfRange(bytes, 8, 16);
		long lnHigh = ByteBuffer.wrap(bytesUpper).getLong();
		long lnLow = ByteBuffer.wrap(bytesLower).getLong();
		return new UUID(lnHigh, lnLow);
	}
	
	private static void swapBytesInPlace(byte[] buffer, int indexFirst, int indexSecond) {
		byte swap = buffer[indexFirst];
		buffer[indexFirst] = buffer[indexSecond];
		buffer[indexSecond] = swap;
	}
	
	public static byte[] toGuidBytes(UUID value) {
		Objects.requireNonNull(value, "value");
		ByteBuffer bbuf = ByteBuffer.allocate(16);
		bbuf.putLong(value.getMostSignificantBits());
		bbuf.putLong(value.getLeastSignificantBits());
		byte[] buf = bbuf.array();
		swapBytesInPlace(buf, 0, 3);
		swapBytesInPlace(buf, 1, 2);
		swapBytesInPlace(buf, 4, 5);
		swapBytesInPlace(buf, 6, 7);
		return buf;
	}
}
