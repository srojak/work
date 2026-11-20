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

import srojak.core.SelfDescription;

/**
 * @author Stephen
 *
 */
public interface SQLIndexTypeCodes {

    @SelfDescription("Basic clustering index with no imposed order")
    static final byte Heap = 0;

    @SelfDescription("Clustering index on one or more columns")
    static final byte Clustered = 1;

    @SelfDescription("Row locating index on one or more columns")
    static final byte Nonclustered = 2;

    @SelfDescription("A shredded, persisted representation of XML")
    static final byte XML = 3;

    @SelfDescription("Specialized index for columns of geometry type")
    static final byte Spatial = 4;

    @SelfDescription("Clustering index using column-based storage")
    static final byte ClusteredColumnstore = 5;

    @SelfDescription("Row locating index using column-based storage")
    static final byte NonclusteredColumnstore = 6;

    static final byte NonclusteredHash = 7;
}
