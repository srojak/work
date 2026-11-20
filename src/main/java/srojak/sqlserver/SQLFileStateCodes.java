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

/**
 * @author Stephen
 *
 */
public interface SQLFileStateCodes {

    static final byte Online = 0;
    static final byte Restoring = 1;
    static final byte Recovering = 2;
    static final byte RecoveryPending = 3;
    static final byte Suspect = 4;
    static final byte Offline = 6;
    static final byte Defunct = 7;
}
