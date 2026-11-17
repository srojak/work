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

/**
 * @author Stephen
 *
 *
 * @see https://learn.microsoft.com/en-us/sql/connect/jdbc/setting-the-connection-properties
 */
public interface SQLServerConnectNames {

	static final String AccessToken = "accessToken";
	static final String AccessTokenCallbackClass = "accessTokenCallbackClass";
	static final String ApplicationIntent = "applicationIntent";
	static final String ApplicationName = "applicationName";
	static final String Authentication = "authentication";
	static final String AuthenticationScheme = "authenticationScheme";
	
	static final String BulkCopyForBatchInsertAllowEncryptedValueModifications 
				= "bulkCopyForBatchInsertAllowEncryptedValueModifications";
	static final String BulkCopyForBatchInsertBatchSize = "bulkCopyForBatchInsertBatchSize";
	static final String BulkCopyForBatchInsertCheckConstraints = "bulkCopyForBatchInsertCheckConstraints";
	static final String BulkCopyForBatchInsertFireTriggers = "bulkCopyForBatchInsertFireTriggers";
	static final String BulkCopyForBatchInsertKeepIdentity = "bulkCopyForBatchInsertKeepIdentity";
	static final String BulkCopyForBatchInsertKeepNulls = "bulkCopyForBatchInsertKeepNulls";
	static final String BulkCopyForBatchInsertTableLock = "bulkCopyForBatchInsertTableLock";
	
	static final String CacheBulkCopyMetadata = "cacheBulkCopyMetadata";
	static final String CalcBigDecimalPrecision = "calcBigDecimalPrecision";
	static final String CancelQueryTimeout = "cancelQueryTimeout";
	static final String ClientCertificate = "clientCertificate";
	static final String ClientKey = "clientKey";
	static final String ClientKeyPassword = "clientKeyPassword";
	static final String ColumnEncryptionSetting = "columnEncryptionSetting";
	static final String ConcatNullYieldsNull = "concatNullYieldsNull";
	static final String ConnectRetryCount = "connectRetryCount";
	static final String ConnectRetryInterval = "connectRetryInterval";
	
	static final String Database = "database";
	static final String DefaultTransactionIsolation = "DefaultTransactionIsolation";
	static final String DatetimeParameterType = "datetimeParameterType";
	static final String DelayLoadingLobs = "delayLoadingLobs";
	static final String DisableStatementPooling = "disableStatementPooling";
	static final String Domain = "domain";
	
	static final String EnablePrepareOnFirstPreparedStatementCall = "enablePrepareOnFirstPreparedStatementCall";
	static final String EnclaveAttestationProtocol = "enclaveAttestationProtocol";
	static final String EnclaveAttestationUrl = "xyz";
	static final String Encrypt = "encrypt";
	
	static final String FailoverPartner = "failoverPartner";
	static final String Fips = "fips";
	static final String Gsscredential = "gsscredential";
	static final String HostNameInCertificate = "hostNameInCertificate";
	
	static final String InstanceName = "instanceName";
	static final String IntegratedSecurity = "integratedSecurity";
	static final String Ipaddresspreference = "ipaddresspreference";
	static final String JaasConfigurationName = "jaasConfigurationName";
	
	static final String KeyStoreAuthentication = "keyStoreAuthentication";
	static final String KeyStoreLocation = "keyStoreLocation";
	static final String KeyStorePrincipalId = "keyStorePrincipalId";
	static final String KeyStoreSecret = "keyStoreSecret";
	
	static final String LastUpdateCount = "lastUpdateCount";
	static final String LockTimeout = "lockTimeout";
	static final String LoginTimeout = "loginTimeout";
	
	static final String MaxResultBuffer = "maxResultBuffer";
	static final String MsiClientId = "msiClientId";
	static final String MultiSubnetFailover = "multiSubnetFailover";
	
	static final String PacketSize = "packetSize";
	static final String Password = "password";
	static final String Port = "port";
	static final String PrepareMethod = "prepareMethod";
	
	static final String QueryTimeout = "queryTimeout";
	static final String QuotedIdentifier = "quotedIdentifier";
	
	static final String Realm = "realm";
	static final String Replication = "replication";
	static final String ResponseBuffering = "responseBuffering";
	
	static final String SelectMethod = "selectMethod";
	static final String SendStringParametersAsUnicode = "sendStringParametersAsUnicode";
	static final String SendTemporalDataTypesAsStringForBulkCopy 
				= "sendTemporalDataTypesAsStringForBulkCopy";
	static final String SendTimeAsDatetime = "sendTimeAsDatetime";
	static final String ServerCertificate = "serverCertificate";
	static final String Server = "server";
	static final String ServerNameAsACE = "serverNameAsACE";
	static final String ServerPreparedStatementDiscardThreshold 
				= "ServerPreparedStatementDiscardThreshold";
	static final String ServerSpn = "serverSpn";
	static final String SocketFactoryClass = "socketFactoryClass";
	static final String SocketTimeout = "socketTimeout";
	static final String StatementPoolingCacheSize = "statementPoolingCacheSize";
	static final String SslProtocol = "sslProtocol";
	
	static final String TransparentNetworkIPResolution 
				= "transparentNetworkIPResolution";
	static final String TrustManagerClass = "trustManagerClass";
	static final String TrustManagerConstructorArg = "trustManagerConstructorArg";
	static final String TrustServerCertificate = "trustServerCertificate";
	static final String TrustStore = "trustStore";
	static final String TrustStorePassword = "trustStorePassword";
	static final String TrustStoreType = "trustStoreType";
	
	static final String UseBulkCopyForBatchInsert = "useBulkCopyForBatchInsert";
	static final String UseDefaultGSSCredential = "useDefaultGSSCredential";
	static final String UseDefaultJaasConfig = "useDefaultJaasConfig";
	static final String UseFmtOnly = "useFmtOnly";
	static final String User = "user";
	
	static final String VectorTypeSupport = "vectorTypeSupport";
	static final String WorkstationID = "workstationID";
	static final String XopenStates = "xopenStates";
}
