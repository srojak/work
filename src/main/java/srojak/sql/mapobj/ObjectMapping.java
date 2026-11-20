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
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.sql.DatabaseAction;
import srojak.sql.MappedObjectException;
import srojak.sql.ValueBindingException;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public interface ObjectMapping {

	ClassKey mappedObjectClass();
	FlagsIntTest getFlags();
	Stream<ObjectMappingIssue> issues();
	boolean isValid();
	boolean hasWarnings();
	MappedObjectSource objSource();
	void process() throws MappedObjectException;
	MappedObjectProperty getPropertyForFieldNamed(String strField);
	MappedObjectProperty getPropertyForColumnNamed(String strColumn);
	ResultSet query(Statement stmt) throws SQLException;
	<M extends AbstractMappedObject> M createInstance() 
			throws MappedObjectException;
	<M extends AbstractMappedObject> void populateDefaults(M instance)
			throws MappedObjectException;
	<M extends AbstractMappedObject> void populateFrom(ResultSet rset, M instance) 
			throws SQLException, MappedObjectException;
	<M extends AbstractMappedObject> List<JdbcValueBinding> bindPropertyValues(M instance, DatabaseAction action)
			throws MappedObjectException, ValueBindingException;
	<M extends AbstractMappedObject> List<JdbcValueBinding> bindPropertyValues(M instance, Collection<String> columnNames)
			throws MappedObjectException, ValueBindingException;
	
	static final int FLAGS_VALID = 0x1;
	static final int FLAGS_HAS_WARNINGS = 0x2;
	static final int FLAGS_PROCESSED = 0x4;
	
	static final ObservedActivity ACTIVITY_PROCESS_MAPPING = new SingleActivity("process mapping");
}
