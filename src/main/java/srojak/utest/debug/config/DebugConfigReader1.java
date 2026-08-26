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
package srojak.utest.debug.config;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import srojak.core.InvalidOperationException;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.result.XResult;
import srojak.core.specialized.IntegerCounter;
import srojak.debug.AppDebugMethods;
import srojak.debug.ClassDebugOptions;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigReader2Pass;
import srojak.numerics.ConditionSense;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedConsumer;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class DebugConfigReader1 {
	
	private static final DebugSwitch swDebugClass;
	
	static {
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(DebugConfigReader1.class));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigReader");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writer
			= new ObservationWriterLevelFilterPrintStream(System.out);
		writer.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writer);
		
		XResult result = AppDebugMethods.readDebugPropertiesFromCurrentDir();
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(DebugConfigReader1.class);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);
		
		swDebugClass.write(ObsLevel.NOTICE, "Reading config file");
		
		DebugNexus debug = new DebugNexus();
		
		UnitTestSupervisedVoid<DebugConfigReader2Pass> test1
			= series.<DebugConfigReader2Pass>createVoidInstance(TestIdentifier.name("read config file"), 
					TestOutcome.PASS, () -> {
						DebugConfigReader2Pass reader = new DebugConfigReader2Pass();
						reader.readFrom("switches.xml");		
						return reader;
					});
		test1.execute();
		
		swDebugClass.write(ObsLevel.NOTICE, "Completed reading config file");
		
		StringBuilder sb = new StringBuilder("Switches");
		IntegerCounter counter = new IntegerCounter();
		debug.forEachSwitch(ds -> {
			sb.append("\n  ");
			sb.append(ds);
			counter.increment(1);
		});
		series.writeMessageLine(ObsLevel.NOTICE, sb.toString());
		series.expectValue(TestIdentifier.name("switch count"), "# switches", 
				OrderedComparison.GT, 0, counter.getValue());
		
		DebugSwitch sw1 = debug.getSwitch(
				DebugSwitchTool.makeClassKey("srojak.map", "MapSquareGridArray"));
		series.expectNull(TestIdentifier.name("debug switch"), "MapSquareGridArray", 
				ConditionSense.IS_NOT, sw1);
		series.expectValue(TestIdentifier.name("debug switch"), "locations", 
				true, sw1.showSourceLocations());
		
		ClassDebugOptions options = debug.getClassOptions(DebugConfigReader1.class);
		series.expectNull(TestIdentifier.name("debug options"), "DebugConfigReader1", 
				ConditionSense.IS_NOT, options);
		series.expectValueWhere(TestIdentifier.name("debug options"), "option3",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 1),
				options.getOptionValue("option3"));
		
		UnitTestSupervisedConsumer<DebugNexus> testSetOptionValue
				= series.createConsumerInstance(TestIdentifier.name("debug options"), TestOutcome.FAIL,
						n -> {
							n.setClassOption(DebugConfigReader1.class, "option3", 2);
						});
		testSetOptionValue.expect(InvalidOperationException.class);
		testSetOptionValue.execute(debug);

		series.complete();
	}

	@SuppressWarnings("unused")
	private static DebugConfigReader2Pass readFile(String strFile)
			throws SAXException, IOException, XMLStreamException {
		DebugConfigReader2Pass reader = new DebugConfigReader2Pass();
		reader.readFrom("switches.xml");		
		return reader;
	}
}
