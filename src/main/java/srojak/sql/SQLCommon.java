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

import java.time.ZoneId;
import java.util.Objects;

import srojak.collate.CollationCatalog;
import srojak.core.faults.ExceptionFormatter;
import srojak.core.faults.FaultMethods;
import srojak.sql.collate.SQLCollationCatalog;
import srojak.sql.impl.SqlExceptionFormatter;
import srojak.sql.mapobj.factories.MapObjPropertyFactoryStore;

/**
 * @author Stephen
 *
 * Common methods for SQL database access
 */
public class SQLCommon {
	
	private static ZoneId _zoneServer = ZoneId.systemDefault();

	/**
	 * Prepare the code base for interpretation of JDBC results.
	 */
	public static void makeReady() {
		
		ExceptionFormatter formatter = new SqlExceptionFormatter();
		FaultMethods.useFormatter(formatter);
		
		CollationCatalog.loadInitial();
		
		SQLCollationCatalog.initialize();
		
		MapObjPropertyFactoryStore.initialize();
	}
	
	/**
	 * Get the current value of the implied time zone for the server.
	 * @return The {@code ZoneId} for server date and time data.
	 */
	public static ZoneId getServerZone() {
		return _zoneServer;
	}
	
	/**
	 * Set the value of the implied time zone for the server.
	 * @param idZone The {@code ZoneId} to infer for server date and time data.
	 */
	public static void setServerZone(ZoneId idZone) {
		Objects.requireNonNull(idZone);
		_zoneServer = idZone;
	}
}
