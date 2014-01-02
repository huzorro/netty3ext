/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code bdb je} clear log service 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultClearLogService implements Service {
	private final static Logger logger = LoggerFactory.getLogger(DefaultClearLogService.class);
	private List<SessionConfig> runningList;
	private Map<String, Map<String, SessionConfig>> configMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>>  receiveMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap;	
	/**
	 * 
	 * @param runningList
	 * @param configMap
	 * @param receiveMsgQueueMap
	 * @param responseMsgQueueMap
	 * @param deliverMsgQueueMap
	 * @param reserveMsgQueueMap
	 */
	public DefaultClearLogService(
			List<SessionConfig> runningList,
			Map<String, Map<String, SessionConfig>> configMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>>  receiveMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap) {
		this.runningList = runningList;
		this.configMap = configMap;
		this.receiveMsgQueueMap = receiveMsgQueueMap;
		this.responseMsgQueueMap = responseMsgQueueMap;
		this.deliverMsgQueueMap = deliverMsgQueueMap;
		this.reserveMsgQueueMap = reserveMsgQueueMap;
	}
	public List<String> clear() {
		return clear(null);
	}
	public List<String> clear(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(Map<String, SessionConfig> map : configMap.values()) {
			for(SessionConfig config : map.values()) {
				if(null != configList && !configList.contains(config.getChannelIds())) continue;
				if(runningList.contains(config)) {
					resultList.add(bdbQueueClearLog(config));
				}
			}
		}			
		return resultList;		
	}
	String bdbQueueClearLog(SessionConfig config) {
		String result = String.format("%1$s [%2$s->%3$s]",
				"clear log for bdb queue",
				config.getAttPreffix(), config.getChannelIds());
		try {			
			ifNotNullSoClear(receiveMsgQueueMap.get(config));
			ifNotNullSoClear(responseMsgQueueMap.get(config));
			ifNotNullSoClear(deliverMsgQueueMap.get(config));
			ifNotNullSoClear(reserveMsgQueueMap.get(config));
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
		}
		return result;		
	}
	void ifNotNullSoClear(BdbQueueMap<Long, QFuture<Message>> queue) {
		if(null != queue) queue.clearLog();
	}
	@Override
	public void run() {

	}
	@Override
	public void process() throws Exception {

	}

}
