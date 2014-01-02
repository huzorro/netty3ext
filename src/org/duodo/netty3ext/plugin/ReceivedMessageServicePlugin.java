/**
 * 
 */
package org.duodo.netty3ext.plugin;

import java.util.concurrent.ExecutorService;

import net.xeoh.plugins.base.Plugin;

import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface ReceivedMessageServicePlugin extends Plugin {
	
	public void handler(BdbQueueMap<Long, QFuture<Message>> queue, ExecutorService executor);
}
