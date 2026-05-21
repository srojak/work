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

import srojak.core.StringBox;

/**
 * @author Stephen
 *
 */
public class PropertiesLoader {
	
	private static void loadFrom(Properties properties, IOSupplier<InputStream> opener)
					throws IOException {
		
		try (InputStream stream = opener.get()) {
			properties.load(stream);
		} catch (IOException exc) {
				throw exc;
		}
	}
	
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

	public static void loadFromResource(Properties properties, ClassLoader loaderClass,
			String strName) 
					throws IOException {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(loaderClass, "loaderClass");
		Objects.requireNonNull(strName, "strName");
		loadFrom(properties, () -> loaderClass.getResourceAsStream(strName));
	}
	
	public static boolean tryLoadFromResource(Properties properties, ClassLoader loaderClass,
			String strName, StringBox boxFailure) {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(loaderClass, "loaderClass");
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(boxFailure, "boxFailure");
		return tryLoadFrom(properties, () -> loaderClass.getResourceAsStream(strName), boxFailure);
	}
	
	public static void loadFromDirectory(Properties properties, Path pathDir, String strName)
			throws IOException {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strName, "strName");
		Path pathFile = pathDir.resolve(strName);
		loadFrom(properties, () -> Files.newInputStream(pathFile));
	}
	
	public static boolean tryLoadFromDirectory(Properties properties, Path pathDir, 
			String strName, StringBox boxFailure) {
		Objects.requireNonNull(properties, "properties");
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(boxFailure, "boxFailure");
		Path pathFile = pathDir.resolve(strName);
		return tryLoadFrom(properties, () -> Files.newInputStream(pathFile), boxFailure);
	}
}
