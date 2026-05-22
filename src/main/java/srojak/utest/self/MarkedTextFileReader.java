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
package srojak.utest.self;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Stephen
 *
 */
public class MarkedTextFileReader {
	private Pattern _patternMark;
	private int _nLines;
	private int _nMatches;
	
	public MarkedTextFileReader() {
		_patternMark = Pattern.compile("mark\\s*", Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
		_nLines = 0;
		_nMatches = 0;
	}
	
	public int getLineCount() {
		return _nLines;
	}
	
	public int getMatchCount() {
		return _nMatches;
	}
	
	public void findMarkedLines(Path pathFile, List<String> listMatches)
			throws IOException {
		try (Stream<String> lines = Files.lines(pathFile)) {
			lines.forEach(line -> {
				_nLines++;
				Matcher matchMark = _patternMark.matcher(line);
				if (matchMark.find()) {
					String strModified = matchMark.replaceAll("").stripLeading();
					listMatches.add(strModified);
				}
			});
		} catch (IOException exc) {
			throw exc;
		}
	}
}
