/**
 * 
 */
package org.duodo.netty3ext.factory.session.config;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.duodo.netty3ext.config.session.DefaultServerSessionConfig;
import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 * @param <T>
 *
 */
public class DefaultServerSessionConfigFactory<T> implements Factory<T> {
	private static final Logger logger = LoggerFactory.getLogger(DefaultServerSessionConfigFactory.class);
	private String user;
	private short version;
	private String host;
	private SessionConfig defaultSessionConfig;
	/**
	 * 
	 * @param defaultSessionConfig
	 */
	public DefaultServerSessionConfigFactory(SessionConfig defaultSessionConfig) {
		this.defaultSessionConfig = defaultSessionConfig;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			logger.error("sqlite jdbc load failed", e.getCause());
			Runtime.getRuntime().exit(-1);
		}		
	}
	public  DefaultServerSessionConfigFactory<T> setUser(String user) {
		this.user = user;
		return this;
	}

	public DefaultServerSessionConfigFactory<T> setVersion(short version) {
		this.version = version;
		return this;
	}
	public DefaultServerSessionConfigFactory<T> setHost(String host) {
		this.host = host;
		return this;
	}
	public SessionConfig getDefaultSessionConfig() {
		return defaultSessionConfig;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T create() throws SQLException {
		URL url = ClassLoader.getSystemResource("session.config");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url.getFile());
    	try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT channelids, clienthost, port, user, passwd, version, maxretry, retrytime, maxsessions, " +
					"windows FROM session_config_tbl WHERE user = ? AND clienthost = ? AND version = ? AND status = ?");
			pstmt.setString(1, user);
			pstmt.setString(2, host);
			pstmt.setInt(3, version);
			pstmt.setInt(4, 1);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) return null;
			SessionConfig config = new DefaultServerSessionConfig();
			config.setAttPreffix(defaultSessionConfig.getAttPreffix());
			config.setConfiguration(defaultSessionConfig.getConfiguration());
			config.setChannelIds(rs.getString("channelids"));
			config.setHost(rs.getString("clienthost"));
			config.setPort(rs.getInt("port"));
			config.setUser(rs.getString("user"));
			config.setPasswd(rs.getString("passwd"));
			config.setVersion(rs.getShort("version"));
			config.setMaxRetry(rs.getInt("maxretry"));
			config.setRetryWaitTime(rs.getInt("retrytime"));
			config.setMaxSessions(rs.getInt("maxsessions"));
			config.setWindows(rs.getInt("windows"));
			return (T) config;
		} catch(SQLException e){
			logger.error("The configuration file failed to obtain certification", e.getCause() != null ? e.getCause() : e);
			throw e;
		}finally {
			connection.close();
		}
	}

}
