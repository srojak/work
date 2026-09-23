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
package srojak.xml.stream;

import java.io.InputStream;

import srojak.core.functional.IOSupplier;
import srojak.core.observe.ObservedActivity;
import srojak.core.result.XResultInt;

/**
 * @author Stephen
 *
 */
public interface XmlStreamAdapter {

	XResultInt openAndRead(ObservedActivity activity, boolean bPermissive, IOSupplier<InputStream> supplierStream);
}
