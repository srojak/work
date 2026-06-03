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
package srojak.core.decorated;

import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public interface Decorated<T> {
	T getValue();
	boolean bHasDecorator(NameToken tokenKey);
	Decorator getDecorator(NameToken tokenKey);
	<D extends Decorator> D getDecoratorAs(NameToken tokenKey);
	void putDecorator(Decorator decorator);
	boolean isEqualTo(T other);
	boolean isEqualTo(Decorated<T> other);
	
	default boolean hasDecoratorBooleanValue(NameToken tokenKey, boolean value) {
		Objects.requireNonNull(tokenKey, "tokenKey");
		Decorator decorator = getDecorator(tokenKey);
		if (decorator != null) {
			if (decorator instanceof BooleanDecorator tdecor) {
				if (tdecor.getValue() == value) {
					return true;
				}
			}
		}
		return false;
	}
	
	default boolean hasDecoratorIntValue(NameToken tokenKey, IntPredicate predicate) {
		Objects.requireNonNull(tokenKey, "tokenKey");
		Objects.requireNonNull(predicate, "predicate");
		Decorator decorator = getDecorator(tokenKey);
		if (decorator != null) {
			if (decorator instanceof IntDecorator tdecor) {
				if (predicate.test(tdecor.getValue())) {
					return true;
				}
			}
		}
		return false;
	}
	
	default boolean hasDecoratorLongValue(NameToken tokenKey, LongPredicate predicate) {
		Objects.requireNonNull(tokenKey, "tokenKey");
		Objects.requireNonNull(predicate, "predicate");
		Decorator decorator = getDecorator(tokenKey);
		if (decorator != null) {
			if (decorator instanceof LongDecorator tdecor) {
				if (predicate.test(tdecor.getValue())) {
					return true;
				}
			}
		}
		return false;
	}
	
	default boolean hasDecoratorDoubleValue(NameToken tokenKey, DoublePredicate predicate) {
		Objects.requireNonNull(tokenKey, "tokenKey");
		Objects.requireNonNull(predicate, "predicate");
		Decorator decorator = getDecorator(tokenKey);
		if (decorator != null) {
			if (decorator instanceof DoubleDecorator tdecor) {
				if (predicate.test(tdecor.getValue())) {
					return true;
				}
			}
		}
		return false;
	}
	
	default <V> boolean hasDecoratorObjValue(Class<V> classValue, NameToken tokenKey,
			Predicate<V> predicate) {
		Objects.requireNonNull(tokenKey, "classValue");
		Objects.requireNonNull(tokenKey, "classValue");
		Objects.requireNonNull(predicate, "predicate");
		Decorator decorator = getDecorator(tokenKey);
		if (decorator != null) {
			if (decorator instanceof @SuppressWarnings("rawtypes") ObjDecorator tdecor) {
				if (classValue.isAssignableFrom(tdecor.getValueClass())) {
					@SuppressWarnings("unchecked")
					V value = (V) tdecor.getValue();
					if (predicate.test(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
