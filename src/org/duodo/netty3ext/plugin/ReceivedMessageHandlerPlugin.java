/**
 * 
 */
package org.duodo.netty3ext.plugin;

import net.xeoh.plugins.base.Plugin;

import org.duodo.netty3ext.message.Message;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface ReceivedMessageHandlerPlugin extends Plugin {
	public boolean received(Message message) throws Exception;
}
