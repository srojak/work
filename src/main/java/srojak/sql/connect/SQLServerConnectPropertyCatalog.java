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
import java.util.stream.Stream;

import srojak.core.Optionality;
import srojak.mantle.text.StringRestriction;
import srojak.mantle.text.StringRestrictionLength;
import srojak.mantle.tprops.TypedBooleanPropertyValue;
import srojak.mantle.tprops.TypedClassPropertyValue;
import srojak.mantle.tprops.TypedIntIntervalPropertyValue;
import srojak.mantle.tprops.TypedIntPropertyValue;
import srojak.mantle.tprops.TypedProperty;
import srojak.mantle.tprops.TypedPropertyHashSet;
import srojak.mantle.tprops.TypedPropertySet;
import srojak.mantle.tprops.TypedStringChoicePropertyValue;
import srojak.mantle.tprops.TypedStringPropertyValue;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalInt;

/**
 * @author Stephen
 *
 */
public class SQLServerConnectPropertyCatalog 
		implements TypedPropertySet, SQLServerConnectNames {
	private static final TypedPropertyHashSet _properties = new TypedPropertyHashSet();
	
	private static void initialize() {
		TypedProperty property;
		
		_properties.put(new TypedStringPropertyValue(AccessToken, Optionality.OPTIONAL));
		_properties.put(new TypedClassPropertyValue(AccessToken, Optionality.OPTIONAL));
		_properties.put(new TypedStringChoicePropertyValue(ApplicationIntent, Optionality.OPTIONAL, 
				List.of("ReadWrite", "ReadOnly")));
		_properties.put(new TypedStringPropertyValue(ApplicationName, Optionality.OPTIONAL,
				new StringRestrictionLength(128)));
		_properties.put(new TypedStringChoicePropertyValue(Authentication, Optionality.OPTIONAL,
				List.of("NotSpecified",
						"ActiveDirectoryIntegrated",
						"ActiveDirectoryManagedIdentity",
						"ActiveDirectoryMSI",
						"ActiveDirectoryInteractive",
						"ActiveDirectoryServicePrincipal",
						"SqlPassword")));
		_properties.put(new TypedStringChoicePropertyValue(AuthenticationScheme, Optionality.OPTIONAL,
				List.of("NativeAuthentication", "NTLM", "JavaKerberos")));
		
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertAllowEncryptedValueModifications,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedIntPropertyValue(BulkCopyForBatchInsertBatchSize, Optionality.OPTIONAL));
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertCheckConstraints,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertFireTriggers,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertKeepIdentity,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertKeepNulls,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedBooleanPropertyValue(BulkCopyForBatchInsertTableLock,
				Optionality.OPTIONAL, false));
				
		_properties.put(new TypedBooleanPropertyValue(CacheBulkCopyMetadata, Optionality.OPTIONAL, false));
		_properties.put(new TypedBooleanPropertyValue(CalcBigDecimalPrecision, Optionality.OPTIONAL, false));
		_properties.put(new TypedIntPropertyValue(CancelQueryTimeout, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(ClientCertificate, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(ClientKey, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(ClientKeyPassword, Optionality.OPTIONAL));
		_properties.put(new TypedStringChoicePropertyValue(ColumnEncryptionSetting, Optionality.OPTIONAL,
				List.of("Disabled", "Enabled")));
		_properties.put(new TypedStringChoicePropertyValue(ConcatNullYieldsNull, Optionality.OPTIONAL,
				List.of("ON", "OFF")));
		_properties.put(new TypedIntIntervalPropertyValue(ConnectRetryCount, Optionality.OPTIONAL,
				new IntervalInt(IntervalType.CLOSED, 0, 255)));
		_properties.put(new TypedIntIntervalPropertyValue(ConnectRetryInterval, Optionality.OPTIONAL,
				new IntervalInt(IntervalType.CLOSED, 1, 60)));
				
		_properties.put(new TypedStringPropertyValue(Database, Optionality.REQUIRED,
				StringRestriction.length(128)));
		_properties.put(new TypedStringChoicePropertyValue(DefaultTransactionIsolation, Optionality.OPTIONAL,
				List.of("READ_UNCOMMITTED", "READ_COMMITTED", "REPEATABLE_READ", "SERIALIZABLE", "SNAPSHOT")));
		_properties.put(new TypedStringChoicePropertyValue(DatetimeParameterType, Optionality.OPTIONAL,
				List.of("datetime", "datetime2", "datetimeoffset")));
		_properties.put(new TypedBooleanPropertyValue(DelayLoadingLobs,	Optionality.OPTIONAL, true));
		_properties.put(new TypedBooleanPropertyValue(DisableStatementPooling,	Optionality.OPTIONAL, true));
		_properties.put(new TypedStringPropertyValue(Domain, Optionality.OPTIONAL));
		
		_properties.put(new TypedBooleanPropertyValue(EnablePrepareOnFirstPreparedStatementCall,
				Optionality.OPTIONAL, false));
		_properties.put(new TypedStringPropertyValue(EnclaveAttestationProtocol, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(EnclaveAttestationUrl, Optionality.OPTIONAL));		
		_properties.put(new TypedBooleanPropertyValue(Encrypt, Optionality.REQUIRED, true));
		
		_properties.put(new TypedStringPropertyValue(FailoverPartner, Optionality.OPTIONAL));
		_properties.put(new TypedBooleanPropertyValue(Fips,	Optionality.OPTIONAL, false));
		_properties.put(new TypedStringPropertyValue(Gsscredential, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(HostNameInCertificate, Optionality.OPTIONAL));
				
		_properties.put(new TypedStringPropertyValue(InstanceName, Optionality.OPTIONAL,
				StringRestriction.length(128)));
		_properties.put(new TypedBooleanPropertyValue(IntegratedSecurity, Optionality.OPTIONAL, false));
		_properties.put(new TypedStringChoicePropertyValue(Ipaddresspreference, Optionality.OPTIONAL,
				List.of("IPV4First", "IPV6First", "UsePlatformDefault")));
		_properties.put(new TypedStringPropertyValue(JaasConfigurationName, Optionality.OPTIONAL));
		
		_properties.put(new TypedStringPropertyValue(KeyStoreAuthentication, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(KeyStoreLocation, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(KeyStorePrincipalId, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(KeyStoreSecret, Optionality.OPTIONAL));
		
		_properties.put(new TypedBooleanPropertyValue(LastUpdateCount,	Optionality.OPTIONAL, true));
		_properties.put(new TypedIntPropertyValue(LockTimeout, Optionality.OPTIONAL));
		_properties.put(new TypedIntIntervalPropertyValue(LoginTimeout, Optionality.OPTIONAL,
				new IntervalInt(IntervalType.CLOSED, 0, 65535)));
		
		_properties.put(new TypedStringPropertyValue(MaxResultBuffer, Optionality.OPTIONAL));
		_properties.put(new TypedStringPropertyValue(MsiClientId, Optionality.OPTIONAL));
		_properties.put(new TypedBooleanPropertyValue(MultiSubnetFailover, Optionality.OPTIONAL, false));
		
		// The allowed values are -1, 0 or the interval [512, 32767]
		_properties.put(new TypedIntIntervalPropertyValue(PacketSize, Optionality.OPTIONAL,
				new IntervalInt(IntervalType.CLOSED, -1, 32767)));
		_properties.put(new TypedStringPropertyValue(Password, Optionality.OPTIONAL,
				StringRestriction.length(128)));
		_properties.put(new TypedIntIntervalPropertyValue(Port, Optionality.OPTIONAL,
				new IntervalInt(IntervalType.CLOSED, 0, 65535)));
		_properties.put(new TypedStringChoicePropertyValue(PrepareMethod, Optionality.OPTIONAL,
				List.of("prepexec", "prepare", "scopeTempTablesToConnection", "none")));
	}
	
	public SQLServerConnectPropertyCatalog() {
		if (_properties.isEmpty()) {
			initialize();
		}
	}

	@Override
	public boolean isEmpty() {
		return _properties.isEmpty();
	}

	@Override
	public int size() {
		return _properties.size();
	}

	@Override
	public boolean containsKey(String strName) {
		return _properties.containsKey(strName);
	}

	@Override
	public TypedProperty get(String strName) {
		return _properties.get(strName);
	}

	@Override
	public <P extends TypedProperty> P getAs(String strName, Class<?> classProperty) {
		return _properties.getAs(strName, classProperty);
	}

	@Override
	public Stream<TypedProperty> stream() {
		return _properties.stream();
	}

}
