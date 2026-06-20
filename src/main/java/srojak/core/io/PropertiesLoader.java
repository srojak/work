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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.specialized.StringBox;

/**
 * @author Stephen
 *
 */
public class PropertiesLoader {
	
	@SuppressWarnings("unused")
	private static XResult loadFrom(Properties properties, IOSupplier<InputStream> opener) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		try (InputStream stream = opener.get()) {
			properties.load(stream);
			result.setValid();
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	private static boolean tryLoadFrom(Properties properties,
			IOSupplier<InputStream> opener, StringBox boxFailure)
	{
		boxFailure.reset();
		try (InputStream stream = opener.get()) {
			properties.load(stream);
			return true;
		} catch (IOException exc) {
			boxFailure.setContent(exc.getMessage());
			return false;
		}
	}

	public static XResult loadFromResource(Properties properties, ClassLoader loaderClass,
			String strName)  {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(loaderClass, "loaderClass");
		Objects.requireNonNull(strName, "strName");
		XResultStatusCarrier result = new XResultStatusCarrier();
		try (InputStream stream = loaderClass.getResourceAsStream(strName)) {
			properties.load(stream);
			result.setValid();
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public static XResult loadFromDirectory(Properties properties, Path pathDir, String strName) {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strName, "strName");
		Path pathFile = pathDir.resolve(strName);
		XResultStatusCarrier result = new XResultStatusCarrier();
		try (InputStream stream = Files.newInputStream(pathFile)) {
			properties.load(stream);
			result.setValid();
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
