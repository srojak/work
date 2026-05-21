/**
 * 
 */
package srojak.utest.debug.config;

import srojak.core.IntegerCounter;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.debug.DebugNexus;
import srojak.debug.config.DebugConfigReader;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class ConfigReaderBadAttribute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DebugNexus debug = new DebugNexus();
		UnitTestSeries series = new UnitTestSeries("ConfigReaderBadAttribute");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writer
			= new ObservationWriterLevelFilterPrintStream(System.out);
		writer.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writer);
		
		UnitTestSupervisedVoid<DebugConfigReader> test1
			= series.<DebugConfigReader>createVoidInstance("read config file", 
					TestOutcome.PASS, () -> {
						DebugConfigReader reader = new DebugConfigReader();
						reader.readFrom("badattr.xml");		
						return reader;
					});
		test1.execute();
		
		StringBuilder sb = new StringBuilder("Switches");
		IntegerCounter counter = new IntegerCounter();
		debug.forEachSwitch(ds -> {
			sb.append("\n  ");
			sb.append(ds);
			counter.increment(1);
		});
		series.writeMessageLine(ObsLevel.NOTICE, sb.toString());
		series.expectValue("switch count", "# switches", OrderedComparison.GT, 0, counter.getValue());

		series.complete();
	}

}
