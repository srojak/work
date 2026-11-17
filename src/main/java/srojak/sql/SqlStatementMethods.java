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

import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import srojak.collate.Collation;
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
public class SqlStatementMethods {
	
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
	
	public static void setFrom(PreparedStatement stmt, NullableCollatedString nble, int sqlType, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		switch (sqlType) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
			break;
			
		default:
			throw new IllegalArgumentException("incorrect sqlType");
		}
		Collation collation = nble.getCollation();
		if (nble.isNull()) {
			stmt.setNull(pIndex, sqlType);
		} else if (collation.isCharsetLatin1()) {
			stmt.setString(pIndex, nble.value());
		} else {
			Charset cset = collation.getCharset();
			byte[] bytes = nble.value().getBytes(cset);
			stmt.setBytes(pIndex, bytes);
		}
	}
	
	public static void setNcharFrom(PreparedStatement stmt, NullableCollatedString nble, int sqlType, int pIndex)
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		Objects.requireNonNull(nble, "nble");
		switch (sqlType) {
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
			break;
			
		default:
			throw new IllegalArgumentException("incorrect sqlType");
		}
		Collation collation = nble.getCollation();
		if (nble.isNull()) {
			stmt.setNull(pIndex, sqlType);
		} else if (collation.isCharsetLatin1()) {
			stmt.setNString(pIndex, nble.value());
		} else {
			Charset cset = collation.getCharset();
			byte[] bytes = nble.value().getBytes(cset);
			stmt.setBytes(pIndex, bytes);
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
