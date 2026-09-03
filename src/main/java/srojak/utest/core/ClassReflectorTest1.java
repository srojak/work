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
package srojak.utest.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResultOf;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class ClassReflectorTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ClassReflectorTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		TestIdentifier idTest = TestIdentifier.name("reflect");

		Integer i1 = Integer.valueOf(4);
		ClassReflector ref1 = new ClassReflector(i1);
		XResultOf<Method> resultMethod = ref1.getMethod("intValue");
		series.expectResult(idTest, "intValue method", UnitTestConditionXResult.passed(), resultMethod);
		
		Method method = resultMethod.getResult();
		int nValue = 0;
		try {
			nValue = (Integer) method.invoke(i1);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write(ObsLevel.NOTICE, "nValue = " + nValue);
		
		series.complete();
	}

}
