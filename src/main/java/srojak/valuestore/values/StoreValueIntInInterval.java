/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.numerics.ConditionSense;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalInt;

/**
 * @author Stephen
 *
 */
public class StoreValueIntInInterval
		extends StoreValueIntInstance {
	private final ConditionSense _sense;
	private final IntervalInt _interval;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueIntInInterval(NamedKey key, int valueInitial,
			ConditionSense sense, IntervalInt interval) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(interval, "interval");
		_sense = sense;
		_interval = interval;
	}

	public StoreValueIntInInterval(NamedKey key, int valueInitial,
			ConditionSense sense, IntervalType typeInterval, int valueMin, int valueMax) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(typeInterval, "typeInterval");
		_sense = sense;
		_interval = new IntervalInt(typeInterval, valueMin, valueMax);
	}
	
	@Override
	protected void validate(int value) {
		if (!_sense.isExpectedResult(_interval.isInInterval(value))) {
			faultInvalid(getKey(), "value");
		}
	}
}
