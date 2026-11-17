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
package srojak.sql.mapobj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import srojak.core.logic.BooleanFlag;
import srojak.sql.MappedObjectException;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedObject {
	protected final BooleanFlag _fDirty;
	private ObjectMapping _mapping;
	
	protected AbstractMappedObject() {
		_fDirty = new BooleanFlag(false);
		_mapping = null;
	}
	
	void setObjectMapping(ObjectMapping mapping) {
		_mapping = mapping;
	}
	
	public boolean isDirty() {
		return _fDirty.getState();
	}
	
	void friendSetDirty(boolean bState) {
		_fDirty.setState(bState);
	}
	
	public void fillFromResultSet(ResultSet rset) throws SQLException, MappedObjectException {
		Objects.requireNonNull(rset, "rset");
		_mapping.populateFrom(rset, this);
		_fDirty.clear();
	}
}
