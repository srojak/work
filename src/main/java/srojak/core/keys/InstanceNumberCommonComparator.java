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
package srojak.core.keys;

import java.util.Comparator;

import srojak.core.ClassInstanceNumber;
import srojak.core.InstanceNumberCommon;

/**
 * @author Stephen
 *
 */
public class InstanceNumberCommonComparator 
		implements Comparator<InstanceNumberCommon> {

	@Override
	public int compare(InstanceNumberCommon o1, InstanceNumberCommon o2) {
		if (o1 == null) {
			return o2 == null ? 0 : -1;
		} else if (o2 == null) {
			return 1;
		} else if (o1.isExcluded()) {
			return -1;
		} else if (o2.isExcluded()) {
			return 1;
		} else if (o1 instanceof ClassInstanceNumber oc1) {
			if (o2 instanceof ClassInstanceNumber oc2) {
				String name1 = oc1.owningClass().getName();
				String name2 = oc1.owningClass().getName();
				int nCompar = name1.compareTo(name2);
				if (nCompar == 0) {
					nCompar = Integer.compare(oc1.number(), oc2.number());
				}
				return nCompar;
			} else {
				return 1;
			}
		} else if (o2 instanceof ClassInstanceNumber oc2) {
			return -1;
		} else {
			return Integer.compare(o1.number(), o2.number());
		}
	}

}
