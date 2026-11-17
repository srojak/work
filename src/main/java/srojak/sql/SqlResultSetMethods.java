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
package srojak.sql;

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.numerics.nullable.NullableBigInt;
import srojak.numerics.nullable.NullableBoolean;
import srojak.numerics.nullable.NullableDateTime;
import srojak.numerics.nullable.NullableDecimal;
import srojak.numerics.nullable.NullableDouble;
import srojak.numerics.nullable.NullableFloat;
import srojak.numerics.nullable.NullableInt;
import srojak.numerics.nullable.NullableLong;
import srojak.numerics.nullable.NullableSmallint;
import srojak.numerics.nullable.NullableTinyint;
import srojak.numerics.nullable.NullableUUID;

/**
 * @author Stephen
 *
 */
public class SqlResultSetMethods {
	
	public static NullableBoolean getNullableBoolean(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		boolean bValue = rset.getBoolean(columnIndex);
		if (rset.wasNull()) {
			return NullableBoolean.fromNull();
		} else {
			return NullableBoolean.from(bValue);
		}
	}
	
	public static NullableBoolean getNullableBoolean(ResultSet rset, String columnLabel)
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		boolean bValue = rset.getBoolean(columnLabel);
		if (rset.wasNull()) {
			return NullableBoolean.fromNull();
		} else {
			return NullableBoolean.from(bValue);
		}
	}
	
	public static NullableInt getNullableInt(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		int nValue = rset.getInt(columnIndex);
		if (rset.wasNull()) {
			return NullableInt.fromNull();
		} else {
			return NullableInt.from(nValue);
		}
	}
	
	public static NullableInt getNullableInt(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		int nValue = rset.getInt(columnLabel);
		if (rset.wasNull()) {
			return NullableInt.fromNull();
		} else {
			return NullableInt.from(nValue);
		}
	}
	
	public static NullableSmallint getNullableSmallint(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		short nValue = rset.getShort(columnIndex);
		if (rset.wasNull()) {
			return NullableSmallint.fromNull();
		} else {
			return NullableSmallint.from(nValue);
		}
	}
	
	public static NullableSmallint getNullableSmallint(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		short nValue = rset.getShort(columnLabel);
		if (rset.wasNull()) {
			return NullableSmallint.fromNull();
		} else {
			return NullableSmallint.from(nValue);
		}
	}
	
	public static NullableTinyint getNullableTinyint(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		byte nValue = rset.getByte(columnIndex);
		if (rset.wasNull()) {
			return NullableTinyint.fromNull();
		} else {
			return NullableTinyint.from(nValue);
		}
	}
	
	public static NullableTinyint getNullableTinyint(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		byte nValue = rset.getByte(columnLabel);
		if (rset.wasNull()) {
			return NullableTinyint.fromNull();
		} else {
			return NullableTinyint.from(nValue);
		}
	}
	
	public static NullableLong getNullableLong(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		long lnValue = rset.getLong(columnIndex);
		if (rset.wasNull()) {
			return NullableLong.fromNull();
		} else {
			return NullableLong.from(lnValue);
		}
	}
	
	public static NullableLong getNullableLong(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		long lnValue = rset.getLong(columnLabel);
		if (rset.wasNull()) {
			return NullableLong.fromNull();
		} else {
			return NullableLong.from(lnValue);
		}
	}
	
	public static NullableDouble getNullableDouble(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		double dValue = rset.getDouble(columnIndex);
		if (rset.wasNull()) {
			return NullableDouble.fromNull();
		} else {
			return NullableDouble.from(dValue);
		}
	}
	
	public static NullableDouble getNullableDouble(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		double dValue = rset.getDouble(columnLabel);
		if (rset.wasNull()) {
			return NullableDouble.fromNull();
		} else {
			return NullableDouble.from(dValue);
		}
	}
	
	public static NullableFloat getNullableFloat(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		float fValue = rset.getFloat(columnIndex);
		if (rset.wasNull()) {
			return NullableFloat.fromNull();
		} else {
			return NullableFloat.from(fValue);
		}
	}
	
	public static NullableFloat getNullableFloat(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		float fValue = rset.getFloat(columnLabel);
		if (rset.wasNull()) {
			return NullableFloat.fromNull();
		} else {
			return NullableFloat.from(fValue);
		}
	}
	
	public static NullableBigInt getNullableBigInt(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		BigDecimal decimal = rset.getBigDecimal(columnIndex);
		if (rset.wasNull()) {
			return NullableBigInt.fromNull();
		} else {
			return NullableBigInt.from(decimal.toBigIntegerExact());
		}
	}
	
	public static NullableBigInt getNullableBigInt(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		BigDecimal decimal = rset.getBigDecimal(columnLabel);
		if (rset.wasNull()) {
			return NullableBigInt.fromNull();
		} else {
			return NullableBigInt.from(decimal.toBigIntegerExact());
		}
	}
	
	public static NullableDecimal getNullableDecimal(ResultSet rset, MathContext context, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(context, "context");
		BigDecimal decimal = rset.getBigDecimal(columnIndex);
		if (rset.wasNull()) {
			return NullableDecimal.fromNull();
		} else {
			return NullableDecimal.from(decimal, context);
		}
	}
	
	public static NullableDecimal getNullableDecimal(ResultSet rset, MathContext context, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		BigDecimal decimal = rset.getBigDecimal(columnLabel);
		if (rset.wasNull()) {
			return NullableDecimal.fromNull();
		} else {
			return NullableDecimal.from(decimal, context);
		}
	}
	
	public static NullableDateTime getNullableDateTime(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		LocalDateTime dtIn = rset.getObject(columnIndex, LocalDateTime.class);
		if (rset.wasNull()) {
			return NullableDateTime.fromNull();
		} else {
			ZonedDateTime zdtValue = dtIn.atZone(SQLCommon.getServerZone());
			return NullableDateTime.from(zdtValue);
		}
	}
	
	public static NullableDateTime getNullableDateTime(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		LocalDateTime dtIn = rset.getObject(columnLabel, LocalDateTime.class);
		if (rset.wasNull()) {
			return NullableDateTime.fromNull();
		} else {
			ZonedDateTime zdtValue = dtIn.atZone(SQLCommon.getServerZone());
			return NullableDateTime.from(zdtValue);
		}
	}
	
	public static NullableCollatedString getNullableString(ResultSet rset, Collation collation, int columnIndex)
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(collation, "collation");
		if (collation.isCharsetLatin1()) {
			String strValue = rset.getString(columnIndex);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(collation);
			} else {
				return NullableCollatedString.from(collation, strValue);
			}
		} else {
			Charset cset = collation.getCharset();
			byte[] bytes = rset.getBytes(columnIndex);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(collation);
			} else {
				String strValue = new String(bytes, cset);
				return NullableCollatedString.from(collation, strValue);
			}
		}
	}
	
	public static NullableCollatedString getNullableString(ResultSet rset, Collation collation, String columnLabel)
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(columnLabel, "columnLabel");
		if (collation.isCharsetLatin1()) {
			String strValue = rset.getString(columnLabel);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(collation);
			} else {
				return NullableCollatedString.from(collation, strValue);
			}
		} else {
			Charset cset = collation.getCharset();
			byte[] bytes = rset.getBytes(columnLabel);
			if (rset.wasNull()) {
				return NullableCollatedString.fromNull(collation);
			} else {
				String strValue = new String(bytes, cset);
				return NullableCollatedString.from(collation, strValue);
			}
		}
	}
	
	public static NullableCollatedString getNullableNCString(ResultSet rset, Collation collation, int columnIndex)
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(collation, "collation");
		String strValue = rset.getNString(columnIndex);
		if (rset.wasNull()) {
			return NullableCollatedString.fromNull(collation);
		} else {
			return NullableCollatedString.from(collation, strValue);
		}
	}
	
	public static NullableCollatedString getNullableNCString(ResultSet rset, Collation collation, String columnLabel)
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(columnLabel, "columnLabel");
		String strValue = rset.getNString(columnLabel);
		if (rset.wasNull()) {
			return NullableCollatedString.fromNull(collation);
		} else {
			return NullableCollatedString.from(collation, strValue);
		}
	}
	
	public static NullableUUID getNullableUUIDMapped(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		byte[] bytes = rset.getBytes(columnIndex);
		if (rset.wasNull()) {
			return NullableUUID.fromNull();
		} else {
			return NullableUUID.fromGuidBytes(bytes);
		}
	}
	
	public static NullableUUID getNullableUUIDMapped(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		byte[] bytes = rset.getBytes(columnLabel);
		if (rset.wasNull()) {
			return NullableUUID.fromNull();
		} else {
			return NullableUUID.fromGuidBytes(bytes);
		}
	}
	
	public static RowVersion getRowVersion(ResultSet rset, int columnIndex) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		byte[] bytes = rset.getBytes(columnIndex);
		if (rset.wasNull()) {
			return new RowVersionValue();
		} else {
			return new RowVersionValue(bytes);
		}
	}
	
	public static RowVersion getRowVersion(ResultSet rset, String columnLabel) 
			throws SQLException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(columnLabel, "columnLabel");
		byte[] bytes = rset.getBytes(columnLabel);
		if (rset.wasNull()) {
			return new RowVersionValue();
		} else {
			return new RowVersionValue(bytes);
		}
	}
}
