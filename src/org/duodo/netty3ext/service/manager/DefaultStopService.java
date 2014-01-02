/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;
import org.duodo.netty3ext.session.Session;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultStopService implements Service {
	private static final Logger logger = LoggerFactory.getLogger(DefaultStopService.class);
	private List<SessionConfig> runningList;
	private Map<String, Map<String, SessionConfig>> configMap;
	private Map<SessionConfig, SessionPool> sessionPoolMap;
	private Map<SessionConfig, ExecutorService> executorServiceMap;
	private Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap;
	private Map<SessionConfig, ScheduledExecutorService> externalScheduleExecutorMap;
	private Map<SessionConfig, ClientBootstrap> clientBootstrapMap;
	private Map<SessionConfig, ServerBootstrap> serverBootstrapMap;
	private Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap;
	private Map<SessionConfig, Factory<Session>> sessionFactoryMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap;
	
	/*
	 * @param configMap
	 * @param sessionPoolMap
	 * @param executorServiceMap
	 * @param scheduleExecutorMap
	 * @param externalScheduleExecutorMap
	 * @param clientBootstrapMap
	 * @param serverBootstrapMap
	 */
	public DefaultStopService(
			List<SessionConfig> runningList,
			Map<String, Map<String, SessionConfig>> configMap,
			Map<SessionConfig, SessionPool> sessionPoolMap,
			Map<SessionConfig, ExecutorService> executorServiceMap,
			Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap,
			Map<SessionConfig, ScheduledExecutorService> externalScheduleExecutorMap,
			Map<SessionConfig, ClientBootstrap> clientBootstrapMap,
			Map<SessionConfig, ServerBootstrap> serverBootstrapMap,
			Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap,
			Map<SessionConfig, Factory<Session>> sessionFactoryMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap) {
		this.runningList = runningList;
		this.configMap = configMap;
		this.sessionPoolMap = sessionPoolMap;
		this.executorServiceMap = executorServiceMap;
		this.scheduleExecutorMap = scheduleExecutorMap;
		this.externalScheduleExecutorMap = externalScheduleExecutorMap;
		this.clientBootstrapMap = clientBootstrapMap;
		this.serverBootstrapMap = serverBootstrapMap;
		this.pluginManagerUtilMap = pluginManagerUtilMap;
		this.sessionFactoryMap = sessionFactoryMap;
		this.receiveMsgQueueMap = receiveMsgQueueMap;
		this.deliverMsgQueueMap = deliverMsgQueueMap;
		this.reserveMsgQueueMap = reserveMsgQueueMap;
	}
	
	public List<String> stop() {
		return stop(null);
	}
	public List<String> stop(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(Map<String, SessionConfig> map : configMap.values()) {
			for(SessionConfig config : map.values()) {
				if(null != configList && !configList.contains(config.getChannelIds())) continue;
				if(runningList.contains(config)) {
					try {
						resultList.add(externalScheduleExecutorShutdown(config));
						resultList.add(sessionPoolShutdown(config));
						resultList.add(scheduleExecutorShutdown(config));
						resultList.add(pluginManagerUtilShutdown(config));
						resultList.add(executorServiceShutdown(config));
						resultList.add(clientBootstrapShutdown(config));
						resultList.add(serverBootstrapShutdown(config));
						resultList.add(sessionFactoryShutdown(config));
						resultList.add(receiveMsgQueueShutdown(config));
						resultList.add(reserveMsgQueueShutdown(config));
						resultList.add(deliverMsgQueueShutdown(config));
						resultList.add(reserveMsgQueueShutdown(config));
						runningList.remove(config);
					} catch (Exception e) {
						logger.error("stop service [FAILS]", e.getCause() != null ? e.getCause() : e);
					}
				}
			}
		}		
		return resultList;
	}
	
	String externalScheduleExecutorShutdown(SessionConfig config) throws Exception{
		String result = String.format("%1$s [%2$s->%3$s]",
				"externalScheduleExecutor contains poolwatch shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {			
			externalScheduleExecutorMap.get(config).shutdownNow();
			externalScheduleExecutorMap.remove(config);
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;
	}
	String sessionPoolShutdown(SessionConfig config) throws Exception {
		String result = String.format("%1$s [%2$s->%3$s]",
				"close the session of pool",
				config.getAttPreffix(), config.getChannelIds());
		try {			
			sessionPoolMap.get(config).close();
			sessionPoolMap.remove(config);
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;		
	}
	String scheduleExecutorShutdown(SessionConfig config) throws Exception{
		String result = String.format("%1$s [%2$s->%3$s]",
				"scheduleExecutorMap contains resubmit shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {			
			scheduleExecutorMap.get(config).shutdownNow();
			scheduleExecutorMap.remove(config);
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;		
	}
	
	String executorServiceShutdown(SessionConfig config) throws Exception{
		String result = String.format("%1$s [%2$s->%3$s]",
				"executorServiceMap contains handle the task shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			executorServiceMap.get(config).shutdownNow();
			executorServiceMap.remove(config);
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;
	}
	String clientBootstrapShutdown(SessionConfig config) throws Exception {
		String result = String.format("%1$s [%2$s->%3$s]",
				"clientBootstrapMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(clientBootstrapMap.get(config) != null) {
				clientBootstrapMap.get(config).shutdown();
				clientBootstrapMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;
	}
	String serverBootstrapShutdown(SessionConfig config) throws Exception {
		String result = String.format("%1$s [%2$s->%3$s]",
				"serverBootstrapMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(serverBootstrapMap.get(config) != null) {
				serverBootstrapMap.get(config).shutdown();
				serverBootstrapMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;
	}
	String pluginManagerUtilShutdown(SessionConfig config) throws Exception {
		String result = String.format("%1$s [%2$s->%3$s]",
				"pluginManagerUtil shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(pluginManagerUtilMap.get(config) != null) {
				pluginManagerUtilMap.get(config).shutdown();
				pluginManagerUtilMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;		
	}
	
	String sessionFactoryShutdown(SessionConfig config) throws Exception {
		String result = String.format("%1$s [%2$s->%3$s]",
				"sessionFactoryMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(sessionFactoryMap.get(config) != null) {
				sessionFactoryMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;		
	}
	
	String receiveMsgQueueShutdown(SessionConfig config) {
		String result = String.format("%1$s [%2$s->%3$s]",
				"receiveMsgQueueMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(receiveMsgQueueMap.get(config) != null) {
				receiveMsgQueueMap.get(config).close();
				receiveMsgQueueMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;			
	}
	String deliverMsgQueueShutdown(SessionConfig config) {
		String result = String.format("%1$s [%2$s->%3$s]",
				"deliverMsgQueueMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(deliverMsgQueueMap.get(config) != null) {
				deliverMsgQueueMap.get(config).close();
				deliverMsgQueueMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;	
	}
	
	String responseMsgQueueShutdown(SessionConfig config) {
		String result = String.format("%1$s [%2$s->%3$s]",
				"responseMsgQueueMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(responseMsgQueueMap.get(config) != null) {
				responseMsgQueueMap.get(config).close();
				responseMsgQueueMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;			
	}
	
	String reserveMsgQueueShutdown(SessionConfig config) {
		String result = String.format("%1$s [%2$s->%3$s]",
				"responseMsgQueueMap shutdown",
				config.getAttPreffix(), config.getChannelIds());
		try {
			if(reserveMsgQueueMap.get(config) != null) {
				reserveMsgQueueMap.get(config).close();
				reserveMsgQueueMap.remove(config);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
			throw e;
		}
		return result;		
	}
	@Override
	public void run() {
	}

	@Override
	public void process() throws Exception {
	}

}
