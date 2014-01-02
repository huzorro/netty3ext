/**
 * 
 */
package org.duodo.netty3ext.plugin;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import net.xeoh.plugins.base.Plugin;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface SubmitMessageServicePlugin extends Plugin {
	/**
	 * 
	 * @param queueMap
	 * @param executor
	 * @param configMap
	 * @param config
	 */
	public void handler(
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> queueMap,
			ExecutorService executor,
			Map<String, SessionConfig> configMap,
			SessionConfig config);
	/**
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void submit(Message message) throws Exception;
}
