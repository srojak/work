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
package srojak.sql.connect;

import java.util.List;
import java.util.Objects;

import srojak.core.excepts.RequiredEntityNotPresentException;
import srojak.mantle.tprops.TypedBooleanProperty;
import srojak.mantle.tprops.TypedClassProperty;
import srojak.mantle.tprops.TypedIntProperty;
import srojak.mantle.tprops.TypedProperty;
import srojak.mantle.tprops.TypedPropertyHashSet;
import srojak.mantle.tprops.TypedStringChoiceProperty;
import srojak.mantle.tprops.TypedStringProperty;

/**
 * @author Stephen
 *
 */
public class SQLServerConnectionStringFactory {
	private final SQLServerConnectPropertyCatalog _catalog;
	private final TypedPropertyHashSet _properties;
	private final String _strServer;
	private int _port;
	
	private static final int MSSQL_PORT = 1433;
	private static final String _leader = "jdbc:sqlserver://";
	
	public SQLServerConnectionStringFactory(String strServer) {
		Objects.requireNonNull(strServer, "strServer");
		if (strServer.isBlank()) {
			throw new IllegalArgumentException("strServer is blank");
		}
		_catalog = new SQLServerConnectPropertyCatalog();
		_properties = new TypedPropertyHashSet();
		_strServer = strServer;
		_port = MSSQL_PORT;
	}
	
	public String server() {
		return _strServer;
	}
	
	public int port() {
		return _port;
	}
	
	public void set(String strName, boolean value) {
		Objects.requireNonNull(strName, "strName");
		TypedBooleanProperty property = _catalog.getAs(strName, TypedBooleanProperty.class);
		TypedBooleanProperty p2 = (TypedBooleanProperty) property.copy();
		p2.setValue(value);
		_properties.put(p2);
	}
	
	public void set(String strName, int value) {
		Objects.requireNonNull(strName, "strName");
		TypedIntProperty property = _catalog.getAs(strName, TypedIntProperty.class);
		TypedIntProperty p2 = (TypedIntProperty) property.copy();
		p2.setValue(value);
		_properties.put(p2);
	}
	
	public void set(String strName, String strValue) {
		Objects.requireNonNull(strName, "strName");
		TypedStringProperty property = _catalog.getAs(strName, TypedStringProperty.class);
		TypedStringProperty p2 = (TypedStringProperty) property.copy();
		p2.setValue(strValue);
		_properties.put(p2);
	}
	
	public void set(String strName, Class<?> classValue) {
		Objects.requireNonNull(strName, "strName");
		TypedClassProperty property = _catalog.getAs(strName, TypedClassProperty.class);
		TypedClassProperty p2 = (TypedClassProperty) property.copy();
		p2.setValue(classValue);
		_properties.put(p2);
	}
	
	public List<String> getAllowableChoices(String strName) {
		Objects.requireNonNull(strName, "strName");
		TypedStringChoiceProperty property = _catalog.getAs(strName, TypedStringChoiceProperty.class);
		return property.choices();
	}
	
	public String create() {
		StringBuilder sb = new StringBuilder(_leader);
		sb.append(_strServer);
		sb.append(':').append(_port);
		List<TypedProperty> listActive = _properties.stream().filter(p -> p.isRequired() || p.isSet()).toList();
		for (TypedProperty p : listActive) {
			if (p.isRequired() && !p.isSet()) {
				throw new RequiredEntityNotPresentException("required property " + p.getName() + " is not set");
			}
			sb.append(';').append(p.getName()).append('=').append(p.valueAsString());
		}
		return sb.toString();
	}
}
