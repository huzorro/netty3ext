/**
 * 
 */
package org.duodo.netty3ext.factory.session.config;

import org.duodo.netty3ext.config.session.SessionConfig;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 */
public class DownstreamServerSessionConfigFactory<T> extends
		DefaultServerSessionConfigFactory<T> {
	/**
	 * 
	 * @param defaultSessionConfig
	 */
	public DownstreamServerSessionConfigFactory(SessionConfig defaultSessionConfig) {
		super(defaultSessionConfig);
	}
	
}
