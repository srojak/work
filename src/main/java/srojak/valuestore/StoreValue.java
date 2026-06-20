/**
 * 
 */
package srojak.valuestore;

import srojak.core.Named;
import srojak.core.keys.NamedKey;
import srojak.valuestore.values.StoreValueCalculationBase;

/**
 * @author Stephen
 *
 * the interface that any object holding a value in a global store must provide.
 */
public interface StoreValue
		extends Named {
	
	/**
	 * Gets the key for the value.
	 * All keys are of type {@code NamedKey}.
	 * @return the key for the value.
	 */
	NamedKey getKey();
	
	/**
	 * Can this value be set directly?
	 * @return {@code true} if the value can be set directly.
	 */
	boolean canSet();
	
	StoreValueCalculationBase getCalculation();
}
