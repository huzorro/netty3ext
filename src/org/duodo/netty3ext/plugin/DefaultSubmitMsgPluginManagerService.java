/**
 * 
 */
package org.duodo.netty3ext.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 * 
 */
public class DefaultSubmitMsgPluginManagerService implements Service {
	private Logger logger = LoggerFactory
			.getLogger(DefaultSubmitMsgPluginManagerService.class);
	private Map<String, SessionConfig> configMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap;
	private Map<SessionConfig, ExecutorService> executorServiceMap;
	private Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap;
	private List<String> configList;
	/**
	 * 
	 * @param configMap
	 * @param queueMap
	 * @param executorServiceMap
	 * @param pluginManagerUtilMap
	 */
	public DefaultSubmitMsgPluginManagerService(
			Map<String, SessionConfig> configMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap,
			Map<SessionConfig, ExecutorService> executorServiceMap,
			Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap) {
		this(configMap, queueMap, executorServiceMap, pluginManagerUtilMap,
				null);
	}
	/**
	 * 
	 * @param configMap
	 * @param queueMap
	 * @param executorServiceMap
	 * @param pluginManagerUtilMap
	 * @param configList
	 */
	public DefaultSubmitMsgPluginManagerService(
			Map<String, SessionConfig> configMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap,
			Map<SessionConfig, ExecutorService> executorServiceMap,
			Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap,
			List<String> configList) {
		this.configMap = configMap;
		this.queueMap = queueMap;
		this.executorServiceMap = executorServiceMap;
		this.pluginManagerUtilMap = pluginManagerUtilMap;
		this.configList = configList;
	}


	@Override
	public void run() {
		try {
			process();
		} catch (Exception e) {
			logger.error("submit the message failed to run plug-management services", e.getCause() != null ? e.getCause() : e);
		}
	}

	@Override
	public void process() throws Exception {
        for(final SessionConfig config : configMap.values()) {
        	if(configList != null && !configList.contains(config.getChannelIds())) continue;
        	create(config);
        }	

	}

	protected void create(SessionConfig config) {
		Collection<SubmitMessageServicePlugin> plugins = pluginManagerUtilMap
				.get(config).getPlugins(SubmitMessageServicePlugin.class);
		for(SubmitMessageServicePlugin plugin : plugins) {
			plugin.handler(queueMap, executorServiceMap.get(config), configMap, config);
		}
	}

}
