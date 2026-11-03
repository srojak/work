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
package srojak.debug.config.impl;

import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import srojak.core.Named;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelInstancePolicyHolder;
import srojak.xml.stream.work.StreamElementDeferredProductBase;

/**
 * @author Stephen
 *
 */
public class InstancePolicyProduct 
		extends StreamElementDeferredProductBase
		implements Named {
	private final String _name;
	private final ObsLevelInstancePolicyHolder _policy;

	/**
	 * @param nameElement
	 * @param locStartElement
	 */
	public InstancePolicyProduct(QName nameElement, Location locStartElement, String strName, ObsLevel levelDefault) {
		super(nameElement, locStartElement);
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(levelDefault, "levelDefault");
		_name = strName;
		_policy = new ObsLevelInstancePolicyHolder(levelDefault);
	}

	@Override
	public String getName() {
		return _name;
	}

	public ObsLevelInstancePolicyHolder policy() {
		return _policy;
	}
}
