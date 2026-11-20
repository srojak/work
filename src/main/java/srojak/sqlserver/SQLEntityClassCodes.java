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
public interface SQLEntityClassCodes {
    static final byte Database = 0;
    static final byte ObjectOrColumn = 1;
    static final byte Parameter = 2;
    static final byte Schema = 3;
    static final byte DatabasePrincipal = 4;
    static final byte Assembly = 5;
    static final byte Type = 6;
    static final byte Index = 7;
    static final byte UDTableTypeColumn = 8;
    static final byte XmlSchemaCollection = 10;
    static final byte DatabaseTriggerDDL = 12;
    static final byte ServerTriggerDDL = 13;
    static final byte MessageType = 15;
    static final byte ServiceContract = 16;
    static final byte Service = 17;
    static final byte RemoteServiceBinding = 18;
    static final byte Route = 19;
    static final byte Dataspace = 20;
    static final byte PartitionFunction = 21;
    static final byte DatabaseFile = 22;
    static final byte PlanGuide = 27;
}
