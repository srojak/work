/**
 * 
 */
package srojak.utest.debug.config;

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
public class ConfigReaderBadLevel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DebugNexus debug = new DebugNexus();
		UnitTestSeries series = new UnitTestSeries("ConfigReaderBadLevel");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writerSysout = new ObservationWriterLevelFilterPrintStream(System.out);
		writerSysout.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writerSysout);
		
		UnitTestSupervisedVoid<DebugConfigReader> test1
			= series.<DebugConfigReader>createVoidInstance("read config file", 
					TestOutcome.PASS, () -> {
						DebugConfigReader reader = new DebugConfigReader();
						reader.setObservationWriter(writerSysout);
						reader.readFrom("badlevel.xml");		
						return reader;
					});
		test1.execute();
		
		series.complete();

	}

}
