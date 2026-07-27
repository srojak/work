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
package srojak.core.text;

/**
 * @author Stephen
 *
 */
public class LabeledEnvelopeStub<T>
	extends LabeledEnvelopeBase<T> {

	/**
	 * @param strLabel
	 */
	public LabeledEnvelopeStub(String strLabel) {
		super(strLabel);
	}

	@Override
	public T getWrapped() {
		return null;
	}

	@Override
	public boolean isValueEqual(T value) {
		return false;
	}

	@Override
	public boolean hasObject() {
		return false;
	}

	@Override
	public Object getWrappedObject() {
		return null;
	}

	@Override
	public Object getObject() {
		return null;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

}
