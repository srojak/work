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
package srojak.utest.identifiers;

import java.util.Objects;

import srojak.core.observe.SourceLocation;
import srojak.utest.TestIdentifier;
import srojak.utest.TestLocationIdentifier;

/**
 * @author Stephen
 *
 */
public class SingleNameLocationTestIdentifier
		extends SingleNameTestIdentifier
		implements TestLocationIdentifier {
	private SourceLocation _location;
	
	public SingleNameLocationTestIdentifier(String strName) {
		super(strName);
		_location = SourceLocation.caller(4);
	}

	@Override
	public String getText() {
		return super.getText() + " method " + _location.getMethodName() 
			+ ", line " + _location.getLineNumber();
		
	}

	@Override
	public TestInstanceIdentifier createInstance() {
		return new TestInstanceIdentifier(super.getText() + " line=" + _location.getLineNumber());
	}

	@Override
	public TestIdentifier setLocation(SourceLocation location) {
		Objects.requireNonNull(location, "location");
		_location = location;
		return this;
	}

	@Override
	public TestIdentifier setCallingLocation() {
		_location = SourceLocation.caller();
		return this;
	}

}
