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
package srojak.core.events;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ActionStatusEvent 
		extends CoreEvent 
		implements ActionStatusCodes, StateChangeCodes {
	private final int _id;
	private final int _status;

	/**
	 * @param source
	 */
	public ActionStatusEvent(Object source, int idRef, int status) {
		super(source);
		_id = idRef;
		_status = status;
	}
	
	public int getReferent() {
		return _id;
	}
	
	public int getStatus() {
		return _status;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", refID = ");
		sb.append(_id);
		sb.append(", status = ");
		sb.append(_status);
	}

}
