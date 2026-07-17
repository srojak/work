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
package srojak.core.containers;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenTaggedObject;

/**
 * @author Stephen
 *
 */
public class NTTObjectCarrier 
		implements NameTokenTaggedObject {
	private final NameToken _name;
	private final Object _object;

	public NTTObjectCarrier(NameToken tokenName, Object obj) {
		Objects.requireNonNull(tokenName, "tokenName");
		Objects.requireNonNull(obj, "obj");
		_name = tokenName;
		_object = obj;
	}

	@Override
	public NameToken getNameTag() {
		return _name;
	}

	@Override
	public boolean isNameTagEqual(NameToken token) {
		return _name.equals(token);
	}

	@Override
	public boolean isNameTagEqual(String strName) {
		return _name.isNameEqual(strName);
	}

	@Override
	public Object getObject() {
		return _object;
	}

	@Override
	public String toString() {
		return "ObjectCarrier [_name=" + _name + "]";
	}
}
