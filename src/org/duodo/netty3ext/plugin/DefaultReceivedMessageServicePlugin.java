/**
 * 
 */
package org.duodo.netty3ext.plugin;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import net.xeoh.plugins.base.PluginInformation;
import net.xeoh.plugins.base.PluginInformation.Information;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import net.xeoh.plugins.base.annotations.meta.Author;
import net.xeoh.plugins.base.annotations.meta.Version;
import net.xeoh.plugins.base.util.PluginManagerUtil;

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
public class DefaultReceivedMessageServicePlugin implements
		ReceivedMessageServicePlugin {

	private Logger logger = LoggerFactory
			.getLogger(DefaultReceivedMessageServicePlugin.class);

	@InjectPlugin
	public PluginManagerUtil pluginManagerUtil;
	
	@Override
	public void handler(final BdbQueueMap<Long, QFuture<Message>> queue,
			final ExecutorService executor) {
		final Collection<ReceivedMessageHandlerPlugin> plugins = pluginManagerUtil
				.getPlugins(ReceivedMessageHandlerPlugin.class);
		final PluginInformation pluginInformation = pluginManagerUtil.getPlugin(PluginInformation.class);
		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						if (Thread.currentThread().isInterrupted())
							break;

						final QFuture<Message> messageFuture = queue.take();
						for (final ReceivedMessageHandlerPlugin plugin : plugins) {
							executor.execute(new Runnable() {

								@Override
								public void run() {
									try {
										if (!plugin.received(messageFuture
												.getMaster()))
											try {
												queue.put(messageFuture);
											} catch (InterruptedException e) {
												logger.error(
														"put message to the queue for failed",
														e.getCause() != null ? e.getCause() : e);
											}
									} catch (Exception e) {
										logger.error(
												"the plugin fails to receive message, the plugin write by {}",
												pluginInformation
														.getInformation(
																Information.AUTHORS,
																plugin), e
														.getCause() != null ? e.getCause() : e);
										try {
											queue.put(messageFuture);
										} catch (InterruptedException e1) {
											logger.error(
													"put message to the queue for failed",
													e1.getCause() != null ? e.getCause() : e);
										}
									}
								}
							});
						}

					}
				} catch (InterruptedException e) {
					logger.error("currentThread interrupted take message in the queue for failed ", e.getCause() != null ? e.getCause() : e);
				}			
			}
		});

	}

}
