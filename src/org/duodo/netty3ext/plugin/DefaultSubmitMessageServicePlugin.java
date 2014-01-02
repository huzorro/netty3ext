/**
 * 
 */
package org.duodo.netty3ext.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import net.xeoh.plugins.base.PluginInformation;
import net.xeoh.plugins.base.PluginInformation.Information;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import net.xeoh.plugins.base.annotations.meta.Author;
import net.xeoh.plugins.base.annotations.meta.Version;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.DefaultFuture;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
@Author(name = "huzorro(huzorro@gmail.com)")
@Version(version = 10000)
@PluginImplementation
public class DefaultSubmitMessageServicePlugin implements
		SubmitMessageServicePlugin {
	private Logger logger = LoggerFactory.getLogger(DefaultSubmitMessageServicePlugin.class);
	@InjectPlugin
	public PluginManagerUtil pluginManagerUtil;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap;
	private Map<String, SessionConfig> configMap;
	private SessionConfig config;
	@Override
	public void handler(
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap,
			final ExecutorService executor, 
			final Map<String, SessionConfig> configMap,
			SessionConfig config) {
		this.queueMap = queueMap;
		this.configMap = configMap;
		this.config = config;
		final Collection<SubmitMessageHandlerPlugin> plugins = pluginManagerUtil
				.getPlugins(SubmitMessageHandlerPlugin.class);
		final PluginInformation pluginInformation = pluginManagerUtil.getPlugin(PluginInformation.class);
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				for(final SubmitMessageHandlerPlugin plugin : plugins) {
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								plugin.submit();
							} catch (Exception e) {
								logger.error(
										"the plugin fails to submit message, the plugin write by {}",
										pluginInformation.getInformation(
												Information.AUTHORS, plugin), e.getCause() != null ? e.getCause() : e);
							}
						}
					});
				}
				
			}
		});
	}

	@Override
	public void submit(Message message) throws InterruptedException {
		try {
			SessionConfig config = configMap.get(message.getChannelIds());
			queueMap.get(config != null ? config : this.config).put(
					new DefaultFuture<Message>(message));
		} catch (InterruptedException e) {
			logger.error("put message to the queue for failed", e.getCause() != null ? e.getCause() : e);
			throw e;
		}
	}


	
}
