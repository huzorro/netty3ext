package org.duodo.netty3ext.manager;

import java.util.Map;

import org.duodo.netty3ext.config.session.SessionConfig;


/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultStatus implements Status {

	private SessionConfig sessionConfig;
	private String channelType;
	private String channelIds;
	private int ratedChannels;
	private boolean isRunning;
	private Map<Object, Integer>  activeChannels;
	private int receiveQueueSize;
	private int deliverQueueSize;
	private int responseQueueSize;
	private int reserveQueueSize;
	
	public DefaultStatus() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSessionConfig(SessionConfig sessionConfig) {
		this.sessionConfig = sessionConfig;

	}

	@Override
	public SessionConfig getSessionConfig() {
		return sessionConfig;
	}

	@Override
	public void setChannelType(String channelType) {
		this.channelType = channelType;

	}

	@Override
	public String getChannelType() {
		return channelType;
	}

	@Override
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;

	}

	@Override
	public String getChannelIds() {
		return channelIds;
	}

	@Override
	public void setRatedChannels(int ratedChannels) {
		this.ratedChannels = ratedChannels;

	}

	@Override
	public int getRatedChannels() {
		return ratedChannels; 
	}

	@Override
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;

	}

	@Override
	public boolean getRunning() {
		return isRunning;
	}

	@Override
	public void setActiveChannels(Map<Object, Integer> activeChannels) {
		this.activeChannels = activeChannels;

	}

	@Override
	public Map<Object, Integer>getActiveChannels() {
		return activeChannels;
	}

	@Override
	public void setReceiveQueueSize(int receiveQueueSize) {
		this.receiveQueueSize = receiveQueueSize;

	}

	@Override
	public int getReceiveQueueSize() {
		return receiveQueueSize;
	}

	@Override
	public void setDeliverQueueSize(int deliverQueueSize) {
		this.deliverQueueSize = deliverQueueSize;

	}

	@Override
	public int getDeliverQueueSize() {
		return deliverQueueSize;
	}

	@Override
	public void setResponseQueueSize(int responseQueueSize) {
		this.responseQueueSize = responseQueueSize;

	}

	@Override
	public int getResponseQueueSize() {
		return responseQueueSize;
	}

	@Override
	public void setReserveQueueSize(int reserveQueueSize) {
		this.reserveQueueSize = reserveQueueSize;
	}

	@Override
	public int getReserveQueueSize() {
		return reserveQueueSize;
	}

	@Override
	public String toString() {
		return String
				.format("DefaultStatus [sessionConfig=%s, channelType=%s, channelIds=%s, ratedChannels=%s, isRunning=%s, activeChannels=%s, receiveQueueSize=%s, deliverQueueSize=%s, responseQueueSize=%s, reserveQueueSize=%s]",
						sessionConfig, channelType, channelIds, ratedChannels,
						isRunning, activeChannels, receiveQueueSize,
						deliverQueueSize, responseQueueSize, reserveQueueSize);
	}

}
