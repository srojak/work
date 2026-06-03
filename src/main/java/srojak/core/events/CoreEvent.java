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

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.EventObject;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class CoreEvent
		extends EventObject {

	/**
	 * @param source
	 */
	public CoreEvent(Object source) {
		super(source);
	}
	
	protected abstract void formatData(StringBuilder sb);
	
	public String toDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append('[');
		formatData(sb);
		sb.append(']');
		return sb.toString();
	}

    /**
     * Throws NotSerializableException, since events derived from CoreEvent are not
     * intended to be serializable.
     */
	@Serial
	private void writeObject(ObjectOutputStream out)
			throws NotSerializableException {
		throw new NotSerializableException("Not serializable.");
	}

    /**
     * Throws NotSerializableException, since events derived from CoreEvent are not
     * intended to be serializable.
     */
	@Serial
    private void readObject(ObjectInputStream in)
    			throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
}
