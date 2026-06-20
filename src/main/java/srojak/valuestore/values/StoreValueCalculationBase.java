/**
 * 
 */
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueCalculationBase {
	private final NamedKey[] _depends;

	protected StoreValueCalculationBase(NamedKey dependentCar, NamedKey[] dependentCdr) {
		Objects.requireNonNull(dependentCar, "valueFirstDepenedent");
		Objects.requireNonNull(dependentCdr, "valuesDependentOn");
		_depends = new NamedKey[dependentCdr.length + 1];
		_depends[0] = dependentCar;
		System.arraycopy(dependentCdr, 0, _depends, 1, dependentCdr.length);
	}
	
	/**
	 * An array of all other keys for values on which this calculation is dependent.
	 * @return The array of other keys for values on which this calculation is dependent.
	 */
	public NamedKey[] getDependencies() {
		return _depends;
	}
	
	public abstract void bindTo(StoreValueKeyed collection);
}
