/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.numerics.ConditionSense;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalDouble;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleInInterval
		extends StoreValueDoubleInstance {
	private final ConditionSense _sense;
	private final IntervalDouble _interval;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueDoubleInInterval(NamedKey key, double valueInitial,
			ConditionSense sense, IntervalDouble interval) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(interval, "interval");
		_sense = sense;
		_interval = interval;
	}
	
	public StoreValueDoubleInInterval(NamedKey key, double valueInitial,
			ConditionSense sense, IntervalType typeInterval, double valueMin, double valueMax) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(typeInterval, "typeInterval");
		_sense = sense;
		_interval = new IntervalDouble(typeInterval, valueMin, valueMax);
	}
	
	@Override
	protected void validate(double value) {
		if (!_sense.isExpectedResult(_interval.isInInterval(value))) {
			faultInvalid(getKey(), "value");
		}
	}
}
