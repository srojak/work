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
package srojak.sqlserver;

import srojak.core.SymbolicName;

/**
 * @author Stephen
 *
 */
public interface SQLDatabasePrincipalTypeCodes {

    @SymbolicName("APPLICATION_ROLE")
    static final String ApplicationRole = "A";

    @SymbolicName("CERTIFICATE_MAPPED_USER")
    static final String CertificateMappedUser = "C";

    @SymbolicName("EXTERNAL_USER")
    static final String ExternalUser = "E";

    @SymbolicName("WINDOWS_GROUP")
    static final String WindowsGroup = "G";

    @SymbolicName("ASYMMETRIC_KEY_MAPPED_USER")
    static final String AsymmetricKeyMappedUser = "K";

    @SymbolicName("DATABASE_ROLE")
    static final String DatabaseRole = "R";

    @SymbolicName("SQL_USER")
    static final String SqlUser = "S";

    @SymbolicName("WINDOWS_USER")
    static final String WindowsUser = "U";

    @SymbolicName("EXTERNAL_GROUP")
    static final String ExternalGroup = "X";
}
