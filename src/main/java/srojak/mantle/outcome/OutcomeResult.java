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
package srojak.mantle.outcome;

/**
 * @author Stephen
 *
 */
public enum OutcomeResult {

	/**
	 * The result was never provided, or has yet to complete.
	 */
	UNDEFINED(false),
	
	/**
	 * The method completed successfully.
	 */
	SUCCESS(true),
	
	/**
	 * The method failed to complete, with errors.
	 */
	ERRORS(true),
	
	/**
	 * The method was cancelled before completion.
	 */
	CANCELLED(true);
	
	private final boolean _validEndState;
	
	private OutcomeResult(boolean validEndState) {
		_validEndState = validEndState;
	}
	
	public boolean isValidEndState() {
		return _validEndState;
	}
}
