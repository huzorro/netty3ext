/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.service.Service;
import org.duodo.netty3ext.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultSessionPoolWatchService implements Service {
	private final Logger logger = LoggerFactory.getLogger(DefaultSessionPoolWatchService.class);
	private Map<SessionConfig, SessionPool>  sessionPoolMap;
	private Map<SessionConfig, Factory<Session>> sessionFactoryMap;
	private Map<String, SessionConfig> sessionConfigMap;
	private Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap;
	private List<String> configList;
	/**
	 * 
	 * @param sessionPoolMap
	 * @param sessionFactoryMap
	 * @param sessionConfigMap
	 * @param scheduleExecutorMap
	 */
	public DefaultSessionPoolWatchService(
			Map<SessionConfig, SessionPool>  sessionPoolMap,
			Map<SessionConfig, Factory<Session>> sessionFactoryMap,
			Map<String, SessionConfig> sessionConfigMap,
			Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap) {
		this(sessionPoolMap, sessionFactoryMap, sessionConfigMap, scheduleExecutorMap, null);
	}
	/**
	 * 
	 * @param sessionPoolMap
	 * @param sessionFactoryMap
	 * @param sessionConfigMap
	 * @param scheduleExecutorMap
	 * @param configList
	 */
	public DefaultSessionPoolWatchService(
			Map<SessionConfig, SessionPool>  sessionPoolMap,
			Map<SessionConfig, Factory<Session>> sessionFactoryMap,
			Map<String, SessionConfig> sessionConfigMap,
			Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap,
			List<String> configList) {
		this.sessionPoolMap = sessionPoolMap;
		this.sessionFactoryMap = sessionFactoryMap;
		this.sessionConfigMap = sessionConfigMap;
		this.scheduleExecutorMap = scheduleExecutorMap;
		this.configList = configList;
	}	

	@Override
	public void run() {
		try {
			process();
		} catch (Exception e) {
			logger.error("session pool watch service fails", e.getCause() != null ? e.getCause() : e);
		}

	}

	@Override
	public void process() throws Exception {
		for(final SessionConfig config : sessionConfigMap.values()) {
        	if(configList != null && !configList.contains(config.getChannelIds())) continue;
        	create(config, sessionPoolMap.get(config));
		}
	}
	protected void create(final SessionConfig config, final SessionPool sessionPool) {
		scheduleExecutorMap.get(config).scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						try {
							for (int i = 0; i < (config.getMaxSessions() - sessionPool
									.size(config.getChannelIds())); i++) {
								try {
									logger.info("channel reconnecting......");
									sessionFactoryMap.get(config).create();
								} catch (Exception e) {
									logger.error(
											"pool watch create session fails",
											e.getCause() != null ? e.getCause() : e);
								}
							}
						} catch (Exception e) {
							logger.error("pool watch scheduled fails", e.getCause() != null ? e.getCause() : e);
						}
					}
				}, config.getPoolWatchTime(), config.getPoolWatchTime(),
				TimeUnit.SECONDS);
	}
}
