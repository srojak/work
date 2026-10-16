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
package srojak.core.observe;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.data.DataPassThrough;
import srojak.core.functional.TextMessageBuilder;
import srojak.core.functional.TextMessageBuilderWithInt;
import srojak.core.observe.text.TextObservationBuilder;
import srojak.core.observe.text.TextObservationBuilderWithInt;
import srojak.core.observe.text.TextObservationDirect;
import srojak.core.observe.text.TextObservationFromSupplier;
import srojak.core.observe.text.TextObservationWithPassThrough;

/**
 * @author Stephen
 *
 * An observation with only text content.
 */
public interface TextObservation {

	String formatToText();
	void formatToText(StringBuilder sb);
	
	public static TextObservation take(String strText) {
		return new TextObservationDirect(strText);
	}
	
	public static TextObservation take(Supplier<String> supplierText) {
		return new TextObservationFromSupplier(supplierText);
	}
	
	public static TextObservation take(DataPassThrough passThrough, 
			Function<DataPassThrough, String> fnMessage) {
		return new TextObservationWithPassThrough(fnMessage, passThrough);
	}
	
	public static TextObservation build(TextMessageBuilder buildMessage) {
		Objects.requireNonNull(buildMessage, "buildMessage");
		return new TextObservationBuilder(buildMessage);
	}
	
	public static TextObservation build(int i, TextMessageBuilderWithInt buildMessage) {
		Objects.requireNonNull(buildMessage, "buildMessage");
		return new TextObservationBuilderWithInt(buildMessage, i);
	}
}
