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
package srojak.core.observe.sources;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.LinkedList;

import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterNamedFileSourceBase 
		implements ObservationWriterSource {
	protected final LinkedList<OpenOption> _listOptions;
	
	protected ObservedActivity ACTIVITY_OPEN_WRITE = new SingleActivity("open for writing");
	
	protected ObservationWriterNamedFileSourceBase() {
		_listOptions = new LinkedList<OpenOption>();
	}

	protected OutputStream open(Path pathFile) throws IOException {
		return Files.newOutputStream(pathFile, _listOptions.toArray(new OpenOption[0]));
	}
}
