/**
 * 
 */
package srojak.valuestore;

/**
 * @author Stephen
 *
 */
public interface StoreValueObj<T>
		extends StoreValue {
	T getValue();
	void setValue(T value);
}
