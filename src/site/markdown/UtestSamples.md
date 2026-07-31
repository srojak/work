# Unit Test Samples

Showing the intended use of the ```srojak.utest``` module.

For all examples:

```
		UnitTestSeries series = new UnitTestSeries("Sample");
```

## Notice

Copyright © 2026 Stephen Rojak.

This file is part of the srojak Java portfolio.

The srojak Java portfolio is free software: you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free Software Foundation,
version 3 of the License.

The srojak Java portfolio is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this portfolio.
If not, see https://www.gnu.org/licenses/.

## Compare an Integer Value for Equality

```
		TestIdentifier ident = TestIdentifier.name("trial");
		
		int nValue = 2;
		
		series.expectValueWhere(ident, "value",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 2), nValue);
		
```

## Compare a Double Value for Equality
Uses the built-in comparison test allowing for a controllable epsilon value,
since double values are not exact.

```
		double dHalf = 1.0d / 2.0d;
		
		series.expectValueWhere(TestIdentifier.name("comparison"), "dSmall", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 0.5d), dHalf);
```

## Compare an Object Value for Equality
In this case, the object ```PolarCoords``` 
does not override the ```hashCode``` 
and ```equals``` methods.
Create an equality comparer:

```
		UnitTestEqualsMethods<PolarCoords> methodEq
			= new UnitTestEqualsMethods<PolarCoords>((e, a) -> 
					DoubleMethods.areEqual(e.getRadius(), a.getRadius())
					&& DoubleMethods.areEqual(e.getTheta(), a.getTheta()));
```

Then the test instance becomes:

```
		S2Coords coordsSouth = new S2Coords(0, 4);
		polarExpected = new PolarCoords(4.0d, Math.PI / 2.0);
		polarActual = PolarCoords.convertFrom(coordsSouth);
		series.expectValueEquals(TestIdentifier.name("convert to polar"),
				"coordsSouth", methodEq, polarExpected, polarActual);
```

## Test a Predicate over Elements in a Collection

Create a method for the elements:

```
		UnitTestClassElementMethods<NameToken> methodElements
			= new UnitTestClassElementMethods<NameToken>(NameToken.class);
```

The test collection is:

```
		List<NameToken> listTokens = List.of(
				NameToken.factory("route1"),
				NameToken.factory("we2can"),
				NameToken.factory("par3"));
```

The test instance is:

```
		TestIdentifier ident = TestIdentifier.name("listNameTokens");
		series.expectAllElementsToHave(ident, "names", methodElements,
			"containing digit",
			token -> token.getName().chars().anyMatch(Character::isDigit),
			listTokens);
```

If we add another token that does not meet the criteria, the test will fail with output like this:

```
ERROR: Test seriesListHavingTestSample, test bad list: names collection of NameToken expecting digit exceptions:
    actual item 3 (token[zero]) does not have digit
```

This is where the formatting method from the element methods class is used.


## Test Methods That Can Throw Exceptions

### Where the Expected Result Is for the Exception Not to Be Thrown

```
		UnitTestSupervisedVoid<DebugConfigReader> test1
			= series.<DebugConfigReader>createVoidInstance(
					TestIdentifier.name("read config file"), 
					TestOutcome.PASS, () -> {
						DebugConfigReader reader = new DebugConfigReader();
						reader.readFrom("switches.xml");		
						return reader;
					});
		test1.execute();
```

### Where the Expected Result Is for a Specific Exception to Be Thrown

In this case, we must tell the instance what exceptions can be expected.
Notice that we also indicate (```TestOutcome.FAIL```) that having no exception thrown
is not a successful outcome.

```
		UnitTestSupervisedConsumer<DebugNexus> testSetOptionValue
				= series.createConsumerInstance(
						TestIdentifier.name("debug options"), TestOutcome.FAIL,
						n -> {
							n.setClassOption(DebugConfigReader1.class, "option3", 2);
						});
		testSetOptionValue.expect(InvalidOperationException.class);
		testSetOptionValue.execute(debug);
```

When the instance object catches the expected exception, it writes to the debug output:

```
NOTICE: Caught exception InvalidOperationException as expected
DETAIL: not open for modification
```

The instance object will accept more than one expected exception.
