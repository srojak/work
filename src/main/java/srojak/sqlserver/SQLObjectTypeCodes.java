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
public interface SQLObjectTypeCodes {
    static final String AggregateFunction = "AF";
    static final String CheckConstraint = "C";
    static final String DefaultConstraint = "D";
    static final String ExternalTable = "ET";
    static final String ForeignKey = "F";
    static final String ScalarFunction = "FN";
    static final String CLRScalarFunction = "FS";
    static final String CLRTableValuedFunction = "FT";
    static final String InlineTableValuedFunction = "IF";
    static final String InternalTable = "IT";
    static final String StoredProcedure = "P";
    static final String CLRStoredProcedure = "PC";
    static final String PlanGuide = "PG";
    static final String PrimaryKey = "PK";
    static final String Rule = "R";
    static final String ReplicationFilter = "RF";
    static final String SystemBaseTable = "S";
    static final String Synonym = "SN";
    static final String Sequence = "SO";
    static final String ServiceQueue = "SQ";
    static final String CLRTrigger = "TA";
    static final String TableValuedFunction = "TF";
    static final String Trigger = "TR";
    static final String TableType = "TT";
    static final String Table = "U";
    static final String UniqueConstraint = "UQ";
    static final String View = "V";
    static final String ExtendedStoredProcedure = "X";
}
