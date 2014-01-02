/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultKickService implements Service {
	private final static Logger logger = LoggerFactory.getLogger(DefaultKickService.class);
	private List<SessionConfig> runningList;
	private Map<String, Map<String, SessionConfig>> configMap;
	private Map<SessionConfig, SessionPool> sessionPoolMap;
	/**
	 * 
	 * @param runningList
	 * @param configMap
	 * @param sessionPoolMap
	 */
	public DefaultKickService(List<SessionConfig> runningList,
			Map<String, Map<String, SessionConfig>> configMap,
			Map<SessionConfig, SessionPool> sessionPoolMap) {
		this.runningList = runningList;
		this.configMap = configMap;
		this.sessionPoolMap = sessionPoolMap;
	}
	public List<String> kick() {
		return kick(null);
	}
	public List<String> kick(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(Map<String, SessionConfig> map : configMap.values()) {
			for(SessionConfig config : map.values()) {
				if(null != configList && !transformed(configList).containsKey(config.getChannelIds())) continue;
				if(runningList.contains(config)) {
					List<String> childList = transformed(configList).get(config.getChannelIds());
					for(String childIds : childList) {
						resultList.add(sessionPoolKick(config, childIds));
					}
				}
			}
		}		
		return resultList;
	}
	
	/**
	 * trans from {@code List<String>} to {@code ListMultimap<String, String>}
	 * <br>trans from {@code [1=a, 1=b, 3=c]}  to {1=[a,b], 3=[c]}
	 * @param configList
	 * @return
	 */
	ListMultimap<String, String> transformed(List<String> configList) {
		ImmutableListMultimap<String, String> transKeyMap = Multimaps.index(configList, new Function<String, String>() {
			@Override
			public String apply(String input) {
				return input.split("=")[0];
			}
		});
		ListMultimap<String, String> transformed = Multimaps.transformValues(transKeyMap, new Function<String, String>() {

			@Override
			public String apply(String input) {
				return input.contains("=") && input.split("=").length > 1 ? input.split("=")[1] : "";
			}
		});
		
		return transformed;
	}
	
	String sessionPoolKick(SessionConfig config, String childIds) {
		String result = String.format("%1$s [%2$s->%3$s->%4$s]",
				"close the session of pool",
				config.getAttPreffix(), config.getChannelIds(), childIds);
		try {
			if(Strings.isNullOrEmpty(childIds)) {
				sessionPoolMap.get(config).close();
			} else {
				sessionPoolMap.get(config).close(childIds);
			}
			result = String.format("%1$s [%2$s]",result, "OK");
			logger.info("{}", result);
		} catch (Exception e) {
			result = String.format("%1$s [%2$s]",result, "FAILS");
			logger.error("{}", result, e.getCause() != null ? e.getCause() : e);
		}
		return result;		
	}	
	@Override
	public void run() {
	}

	public void process() throws Exception {
	}

}
