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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import srojak.numerics.nullable.NullableBoolean;
import srojak.numerics.nullable.NullableDouble;
import srojak.numerics.nullable.NullableFloat;
import srojak.numerics.nullable.NullableInt;
import srojak.numerics.nullable.NullableLong;
import srojak.numerics.nullable.NullableSmallint;
import srojak.numerics.nullable.NullableTinyint;

/**
 * @author Stephen
 *
 */
public class SqlDataMethods {
	
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
	
	public static void setFrom(PreparedStatement stmt, NullableBoolean nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.BIT);
		} else {
			stmt.setBoolean(pIndex, nble.isTrue());
		}
	}
	
	public static void setFrom(PreparedStatement stmt, NullableInt nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.INTEGER);
		} else {
			stmt.setInt(pIndex, nble.value());
		}
	}
	
	public static void setFrom(PreparedStatement stmt, NullableSmallint nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.SMALLINT);
		} else {
			stmt.setShort(pIndex, nble.value());
		}
	}
	
	public static void setFrom(PreparedStatement stmt, NullableTinyint nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.TINYINT);
		} else {
			stmt.setByte(pIndex, nble.value());
		}
	}
	
	public static void setFrom(PreparedStatement stmt, NullableLong nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.BIGINT);
		} else {
			stmt.setLong(pIndex, nble.value());
		}
	}
	
	public static void setFrom(PreparedStatement stmt, NullableDouble nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.DOUBLE);
		} else {
			stmt.setDouble(pIndex, nble.value());
		}		
	}
	
	public static void setFrom(PreparedStatement stmt, NullableFloat nble, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		if (nble.isNull()) {
			stmt.setNull(pIndex, Types.FLOAT);
		} else {
			stmt.setFloat(pIndex, nble.value());
		}		
	}
	
	public static void registerReturnValue(CallableStatement stmt) 
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		stmt.registerOutParameter(1, Types.INTEGER);
	}
	
	public static SprocReturnValue getReturnValue(CallableStatement stmt) 
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		int nValue = stmt.getInt(1);
		if (stmt.wasNull()) {
			return new StoredProcReturnValueBox();
		} else {
			return new StoredProcReturnValueBox(nValue);
		}
	}
}
