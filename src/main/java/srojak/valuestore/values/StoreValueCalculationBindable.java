/**
 * 
 */
package srojak.valuestore.values;

import srojak.core.NameToken;
import srojak.core.field.SetOnce;
import srojak.core.field.SetOnceConditions;
import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueCalculationBindable<C extends StoreValueKeyed>
		extends StoreValueCalculationBase {
	private final SetOnce<C> _boundCollection;	
	
	/**
	 * @param valuesDependentOn
	 */
	public StoreValueCalculationBindable(NamedKey dependentCar, NamedKey[] dependentCdr) {
		super(dependentCar, dependentCdr);
		_boundCollection = new SetOnce<C>(NameToken.factory("BoundCollection"), SetOnceConditions.DEFAULT);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void bindTo(StoreValueKeyed collection) {
		_boundCollection.set((C) collection);
		
	}

	public C getBoundCollection() {
		return _boundCollection.get();
	}
}
