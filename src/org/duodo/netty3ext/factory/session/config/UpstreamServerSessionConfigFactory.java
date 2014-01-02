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
public class UpstreamServerSessionConfigFactory<T> extends
		DefaultServerSessionConfigFactory<T> {
	/**
	 * 
	 * @param defaultSessionConfig
	 */
	public UpstreamServerSessionConfigFactory(SessionConfig defaultSessionConfig) {
		super(defaultSessionConfig);
	}

}
