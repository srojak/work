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
package srojak.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import srojak.core.observe.ObservedActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class ResourceMethods {

	public static XResult readResourceText(ResourceLocator locator, StringBuilder sbText) {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(sbText, "sbText");
		XResultStatusCarrier result = new XResultStatusCarrier(ObservedActivity.READ_RESOURCE);
		try (InputStream input = locator.getResourceStream();
				InputStreamReader readerStream = new InputStreamReader(input)) {
			while (true) {
				int c = readerStream.read();
				if (c == -1) {
					break;
				}
				sbText.append((char) c);
			}
			result.setValid();
		}
		catch (IOException exc) {
			result.caughtThrowable(exc);
		}
		return result;
	}
}
