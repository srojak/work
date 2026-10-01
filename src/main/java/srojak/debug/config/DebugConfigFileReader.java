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
package srojak.debug.config;

import java.nio.file.Path;
import java.util.Objects;

import srojak.core.io.FileExistence;
import srojak.core.io.IONodeIdentifier;
import srojak.core.io.IONodeName;
import srojak.core.io.IONodeType;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.result.XResult;
import srojak.debug.DebugConfigSchema;
import srojak.xml.stream.XmlStreamContentValidatingReaderBase;
import srojak.xml.stream.factories.XmlStreamInputFactory;
import srojak.xml.stream.factories.XmlStreamParserFactory;

/**
 * @author Stephen
 *
 */
public final class DebugConfigFileReader 
		extends XmlStreamContentValidatingReaderBase<DebugConfigParser2> {
	private final XmlStreamInputFactory _factoryInput;
	private ObservationWriterPrintStream _writerClass;
	private ObservationWriterPrintStream _writerParser;

	private static final XmlStreamParserFactory<DebugConfigParser2> _factoryParser
			= new XmlStreamParserFactory<DebugConfigParser2>(DebugConfigParser2.class, () -> new DebugConfigParser2());
	
	public static final ObservedActivity ACTIVITY_GET_SCHEMA = new SingleActivity("reading debug config schema");
	public static final ObservedActivity ACTIVITY_READ = new SingleActivity("reading debug config");

	/**
	 * 
	 */
	public DebugConfigFileReader() {
		super(_factoryParser);
		_factoryInput = new XmlStreamInputFactory(true);
		_writerClass = new ObservationWriterPrintStream(System.err);
		_writerClass.enableLevelFilter();
		_writerParser = new ObservationWriterPrintStream(System.out);
		_writerParser.enableLevelFilter();
		_writerParser.setObsLevel(ObsLevel.WARN);
		_collectObs.addWriter(_writerClass);
		_factoryInput.getObservationCollector().addWriter(_writerClass);;
		_parser.getObservationCollector().addWriter(_writerParser);
	}

	@Override
	protected XResult tryLoadSchema() {
		DebugConfigSchema resourceSchema = new DebugConfigSchema();
		return super.readSchemaFrom(ACTIVITY_GET_SCHEMA, resourceSchema);
	}
	
	public void setClassWriterObsLevel(ObsLevel level) {
		_writerClass.setObsLevel(level);
	}
	
	public void setParserWriterObsLevel(ObsLevel level) {
		_writerParser.setObsLevel(level);
	}
	
	public XResult readConfigFile(Path pathFile, FileExistence exists) {
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		IONodeIdentifier idSource = new IONodeName(IONodeType.FILE, pathFile.toAbsolutePath().toString());
		_parser.startReading(idSource);
		XResult result = super.parseFromFile(_factoryInput, ACTIVITY_READ, pathFile, exists);
		_parser.endReading(idSource);
		return result;
	}
	
	public XResult readConfigFile(String strFile, FileExistence exists) {
		Objects.requireNonNull(strFile, "strFile");
		Objects.requireNonNull(exists, "exists");
		if (strFile.isBlank()) {
			throw new IllegalArgumentException("strFile is blank");
		}
		Path pathFile = Path.of(strFile).normalize();
		IONodeIdentifier idSource = new IONodeName(IONodeType.FILE, pathFile.toAbsolutePath().toString());
		_parser.startReading(idSource);
		XResult result = super.parseFromFile(_factoryInput, ACTIVITY_READ, pathFile, exists);
		_parser.endReading(idSource);
		return result;
	}
}
