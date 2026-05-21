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
package srojak.numerics.intervals;

import java.util.Objects;

import srojak.numerics.IntervalType;

/**
 * @author Stephen
 *
 */
abstract class IntervalBase {
	protected final IntervalType _type;
	private final boolean _bIsDiscrete;
	
	protected IntervalBase(IntervalType type, boolean bIsDiscrete) {
		Objects.requireNonNull(type, "type");
		_type = type;
		_bIsDiscrete = bIsDiscrete;
	}
	
	public IntervalType getIntervalType() {
		return _type;
	}
	
	public boolean isDiscrete() {
		return _bIsDiscrete;
	}
	
	public abstract boolean isDegenerate();
	
	public boolean isLeftClosed() {
		return _type.isLeftClosed();
	}
	
	public boolean isRightClosed() {
		return _type.isRightClosed();
	}
	
	protected abstract void writeMinimumValueTo(StringBuilder sb);
	
	protected abstract void writeMaximumValueTo(StringBuilder sb);
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(_type.charLeftSide);
		writeMinimumValueTo(sb);
		sb.append(", ");
		writeMaximumValueTo(sb);
		sb.append(_type.charRightSide);
		return sb.toString();
	}
}
