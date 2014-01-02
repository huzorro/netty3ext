package org.duodo.netty3ext.manager;

import java.util.Map;

import org.duodo.netty3ext.config.session.SessionConfig;
/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface Status {
	public void setSessionConfig(SessionConfig sessionConfig);
	public SessionConfig getSessionConfig();
	public void setChannelType(String channelType);
	public String getChannelType();
	public void setChannelIds(String channelIds);
	public String getChannelIds();
	public void setRatedChannels(int ratedChannels);
	public int getRatedChannels();
	public void setIsRunning(boolean isRunning);
	public boolean getRunning();
	public void setActiveChannels(Map<Object, Integer> activeChannels);
	public Map<Object, Integer> getActiveChannels(); 
	public void setReceiveQueueSize(int receiveQueueSize);
	public int getReceiveQueueSize();
	public void setDeliverQueueSize(int deliverQueueSize);
	public int getDeliverQueueSize();
	public void setResponseQueueSize(int responseQueueSize);
	public int getResponseQueueSize();
	public void setReserveQueueSize(int reserveQueueSize);
	public int getReserveQueueSize();
}
