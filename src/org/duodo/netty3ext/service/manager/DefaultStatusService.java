/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.manager.DefaultStatus;
import org.duodo.netty3ext.manager.Status;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultStatusService implements Service {
	private List<SessionConfig> runningList;
	private Map<String, Map<String, SessionConfig>> configMap;
	private Map<SessionConfig, SessionPool> sessionPoolMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap;
	/**
	 * 
	 * @param runningList
	 * @param configMap
	 * @param sessionPoolMap
	 * @param receiveMsgQueueMap
	 * @param responseMsgQueueMap
	 * @param deliverMsgQueueMap
	 * @param reserveMsgQueueMap
	 */
	public DefaultStatusService(
			List<SessionConfig> runningList,
			Map<String, Map<String, SessionConfig>> configMap,
			Map<SessionConfig, SessionPool> sessionPoolMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>>  receiveMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap) {
		this.runningList = runningList;
		this.configMap = configMap;
		this.sessionPoolMap = sessionPoolMap;
		this.receiveMsgQueueMap = receiveMsgQueueMap;
		this.responseMsgQueueMap = responseMsgQueueMap;
		this.deliverMsgQueueMap = deliverMsgQueueMap;
		this.reserveMsgQueueMap = reserveMsgQueueMap;
	}
	
	Status fillStatus(SessionConfig config) {
		Status status = new DefaultStatus();
		status.setSessionConfig(config);
		status.setChannelIds(config.getChannelIds());
		status.setChannelType(config.getAttPreffix());
		status.setRatedChannels(config.getMaxSessions());
		if(runningList.contains(config)) {
			status.setIsRunning(true);
			status.setActiveChannels(sessionPoolMap.get(config).size());
			status.setDeliverQueueSize(getQueueSize(deliverMsgQueueMap.get(config)));
			status.setResponseQueueSize(getQueueSize(responseMsgQueueMap.get(config)));
			status.setReceiveQueueSize(getQueueSize(receiveMsgQueueMap.get(config)));
			status.setReserveQueueSize(getQueueSize(reserveMsgQueueMap.get(config)));
		}
		return status;
	}
	int getQueueSize(BdbQueueMap<Long, QFuture<Message>> q){
		return q == null ? 0 : q.size();
	}
	public List<Status> status() {
		return status(null);
	}
	
	public List<Status> status(List<String> configList) {
		List<Status> statusList = new ArrayList<Status>();
		
		for(Map<String, SessionConfig> map : configMap.values()) {
			for(SessionConfig config : map.values()) {
				if(null != configList && !configList.contains(config.getChannelIds())) continue;
				statusList.add(fillStatus(config));
			}
		}
		return statusList;
	}
	public List<String> statusToStr() {
		return statusToStr(null);
	}
	
	public List<String> statusToStr(List<String> configList) {
		List<String> statusList = new ArrayList<String>();
		for(Map<String, SessionConfig> map : configMap.values()) {
			for(SessionConfig config : map.values()) {
				if(null != configList && !configList.contains(config.getChannelIds())) continue;
				statusList.add(fillStatus(config).toString());
			}
		}
		return statusList;
	}
	
	@Override
	public void run() {
	}

	@Override
	public void process() throws Exception {
	}

}
