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
public interface SQLServerPrincipalTypeCodes {

	@SymbolicName("CERTIFICATE_MAPPED_LOGIN")
    static final String CertificateMappedLogin = "C";

    @SymbolicName("EXTERNAL_LOGIN")
    static final String ExternalLogin = "E";

    @SymbolicName("WINDOWS_GROUP")
    static final String WindowsGroup = "G";

    @SymbolicName("ASYMMETRIC_KEY_MAPPED_LOGIN")
    static final String AsymmetricKeyMappedLogin = "K";

    @SymbolicName("COMPONENT_LOGIN")
    static final String ComponentLogin = "M";

    @SymbolicName("SERVER_ROLE")
    static final String ServerRole = "R";

    @SymbolicName("SQL_LOGIN")
    static final String SqlLogin = "S";

    @SymbolicName("WINDOWS_LOGIN")
    static final String WindowsLogin = "U";

    @SymbolicName("EXTERNAL_GROUP")
    static final String ExternalGroup = "X";
}
