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

import java.util.HashMap;
import java.util.List;

import srojak.core.keys.ClassKey;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;
import srojak.utest.core.reflect.ReflectTestA;
import srojak.utest.core.reflect.ReflectTestB;
import srojak.utest.core.reflect.ReflectTestC;
import srojak.utest.core.reflect.ReflectTestD;
import srojak.utest.core.reflect.ReflectTestE;
import srojak.utest.core.reflect.ReflectTestF;
import srojak.utest.core.reflect.ReflectTestI1;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class ClassKeyEvalTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ClassKeyEvalTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		TestIdentifier idTestQual = TestIdentifier.name("findQualifying");
		TestIdentifier idTestBest = TestIdentifier.name("findBest");
		
		UnitTestEqualsMethods<ClassKey> methodEqKey
			= new UnitTestEqualsMethods<ClassKey>((e, a) -> e.equals(a));
		
		HashMap<ClassKey, Integer> map = new HashMap<ClassKey, Integer>();
		map.put(new ClassKey(ReflectTestA.class), Integer.valueOf(1));
		map.put(new ClassKey(ReflectTestB.class), Integer.valueOf(2));
		map.put(new ClassKey(ReflectTestC.class), Integer.valueOf(3));
		map.put(new ClassKey(ReflectTestE.class), Integer.valueOf(5));
		
		Object obj1 = new ReflectTestF();
		List<ClassKey> list1 = ClassKey.findAllQualifyingIn(map.keySet(), obj1.getClass());
		series.expectValueWhere(idTestQual, "class F",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 3), list1.size());
		
		ClassKey key1 = ClassKey.findMostSpecificFor(map.keySet(), obj1.getClass());
		series.expectValueEquals(idTestBest, "class F", methodEqKey, new ClassKey(ReflectTestC.class), key1);
		
		Object obj2 = new ReflectTestD();
		ClassKey key2 = ClassKey.findMostSpecificFor(map.keySet(), obj2.getClass());
		series.expectValueEquals(idTestBest, "class D", methodEqKey, new ClassKey(ReflectTestB.class), key2);
		
		List<ClassKey> list0 = ClassKey.findAllImplementingIn(map.keySet(), ReflectTestI1.class);
		series.expectValueWhere(idTestQual, "interface I1",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 3), list0.size());
		
		series.complete();
	}

}
