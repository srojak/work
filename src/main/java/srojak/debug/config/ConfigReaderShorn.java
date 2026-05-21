/**
 * 
 */
package srojak.utest.debug.config;

import org.xml.sax.SAXParseException;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.debug.DebugNexus;
import srojak.debug.config.DebugConfigReader;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class ConfigReaderShorn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DebugNexus debug = new DebugNexus();
		UnitTestSeries series = new UnitTestSeries("ConfigReaderShorn");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writerSysout = new ObservationWriterLevelFilterPrintStream(System.out);
		writerSysout.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writerSysout);
		
		UnitTestSupervisedVoid<DebugConfigReader> test1
			= series.<DebugConfigReader>createVoidInstance("read config file", 
					TestOutcome.FAIL, () -> {
						DebugConfigReader reader = new DebugConfigReader();
						reader.readFrom("sheared.xml");		
						return reader;
					});
		test1.expect(SAXParseException.class);
		test1.execute();
		
		series.complete();
	}

}
