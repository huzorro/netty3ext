/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.duodo.netty3ext.global.GlobalVars;
import org.duodo.netty3ext.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The default client startup management services
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultClientStartService implements StartServices {
	private final Logger logger = LoggerFactory.getLogger(DefaultClientStartService.class);
	private CombinedConfiguration  config;
	private List<ClientServices> services = new ArrayList<ClientServices>();
	private String configName;
	private String configKey;
	/**
	 * 
	 * @param config
	 */
	public DefaultClientStartService(CombinedConfiguration  config) {
		this(config, "service", "client.service");
	}
	/**
	 * 
	 * @param config
	 * @param configName
	 * @param configKey
	 */
	public DefaultClientStartService(CombinedConfiguration  config, String configName, String configKey) {
		this.config = config;
		this.configName = configName;
		this.configKey = configKey;
		configInit();
	}
	@SuppressWarnings("unchecked")
	private void configInit() {
		PropertiesConfiguration propConfig = (PropertiesConfiguration) config.getConfiguration(configName);
		List<Object> list = propConfig.getList(configKey);
		for(Object service : list) {
			try {
				Class<ClientServices> classz = (Class<ClientServices>) Class.forName((String) service);
				services.add(classz.newInstance());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error("start client to init services fails", e.getCause() != null ? e.getCause() : e);
				Runtime.getRuntime().exit(-1);
			} 
		}
	}
	public List<String> duplexstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(ClientServices service : services) {
			try {
				service.duplexstreamGlobalVarsInit(configList);
				resultList.add("duplex stream globalVars Initialize [OK]");
				logger.info("duplex stream globalVars Initialize [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream globalVars Initialize [FAILS]");
				logger.error("duplex stream globalVars Initialize [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			} 
			try {
				service.duplexstreamServiceInit(configList).process();
				resultList.add("duplex stream service to init [OK]");
				logger.info("duplex stream service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream service to init [FAILS]");
				logger.error("duplex stream service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				Service poolWatchService = service.duplexstreamPoolWatchServiceInit(configList);
				if(poolWatchService != null) {
					poolWatchService.process();
					resultList.add("duplex stream pool watch service to init [OK]");
					logger.info("duplex stream pool watch service to init [OK]");
				}
			} catch (Exception e) {
				resultList.add("duplex stream pool watch service to init [FAILS]");
				logger.error("duplex stream pool watch service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.duplexstreamDeliverServiceInit(configList).process();
				resultList.add("duplex stream deliver service to init [OK]");
				logger.info("duplex stream deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream deliver service to init [FAILS]");
				logger.error("duplex stream submit service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.duplexstreamReserveDeliverServiceInit(configList).process();
				resultList.add("duplex stream reserve deliver service to init [OK]");
				logger.info("duplex stream reserve deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream reserve deliver service to init [FAILS]");
				logger.error("duplex stream reserve deliver service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.duplexstreamDeliverMsgPluginManagerServiceInit(configList).process();
				resultList.add("duplex stream deliver msg plugin manager service to init [OK]");
				logger.info("duplex stream deliver msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream deliver msg plugin manager service to init [FAILS]");
				logger.error("duplex stream deliver msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.duplexstreamResponseMsgPluginManagerServiceInit(configList).process();
				resultList.add("duplex stream response msg plugin manager service to init [OK]");
				logger.info("duplex stream response msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream response msg plugin manager service to init [FAILS]");
				logger.error("duplex stream response msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.duplexstreamReceiverMsgPluginManagerServiceInit(configList).process();
				resultList.add("duplex stream receive msg plugin manager service to init [OK]");
				logger.info("duplex stream receive msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("duplex stream receive msg plugin manager service to init [FAILS]");
				logger.error("duplex stream receive msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
		}
		return resultList;
	}
	public List<String> duplexstreamService() {
		return duplexstreamService(null);
	}
	public List<String> upstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(ClientServices service : services) {
			try {
				service.upstreamGlobalVarsInit(configList);
				resultList.add("up stream globalVars Initialize [OK]");
				logger.info("up stream globalVars Initialize [OK]");
			} catch (Exception e) {
				resultList.add("up stream globalVars Initialize [FAILS]");
				logger.error("up stream globalVars Initialize [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.upstreamServiceInit(configList).process();
				resultList.add("up stream service to init [OK]");
				logger.info("up stream service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream service to init [FAILS]");
				logger.error("up stream service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				Service poolWatchService = service.upstreamPoolWatchServiceInit(configList);
				if(poolWatchService != null) {
					poolWatchService.process();
					resultList.add("up stream pool watch service to init [OK]");
					logger.info("up stream pool watch service to init [OK]");
				}
			} catch (Exception e) {
				resultList.add("up stream pool watch service to init [FAILS]");
				logger.error("up stream pool watch service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.upstreamReceiverMsgPluginManagerServiceInit(configList).process();
				resultList.add("up stream receive msg plugin manager service to init [OK]");
				logger.info("up stream receive msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream receive msg plugin manager service to init [FAILS]");
				logger.error("up stream receive msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
		}
		return resultList;
	}
	public List<String> upstreamService() {
		return upstreamService(null);
	}
	public List<String> downstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(ClientServices service : services) {
			try {
				service.downstreamGlobalVarsInit(configList);
				resultList.add("down stream globalVars Initialize [OK]");
				logger.info("down stream globalVars Initialize [OK]");
			} catch (Exception e) {
				resultList.add("down stream globalVars Initialize [FAILS]");
				logger.error("down stream globalVars Initialize [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.downstreamServiceInit(configList).process();
				resultList.add("down stream service to init [OK]");
				logger.info("down stream service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream service to init [FAILS]");
				logger.error("down stream service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				Service poolWatchService = service.downstreamPoolWatchServiceInit(configList);
				if(poolWatchService != null) {
					poolWatchService.process();
					resultList.add("down stream pool watch service to init [OK]");
					logger.info("down stream pool watch service to init [OK]");
				}
			} catch (Exception e) {
				resultList.add("down stream pool watch service to init [FAILS]");
				logger.error("down stream pool watch service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.downstreamDeliverServiceInit(configList).process();
				resultList.add("down stream deliver service to init [OK]");
				logger.info("down stream deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream deliver service to init [FAILS]");
				logger.error("down stream deliver service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.downstreamReserveDeliverServiceInit(configList).process();
				resultList.add("down stream reserve deliver service to init [OK]");
				logger.info("down stream reserve deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream reserve deliver service to init [FAILS]");
				logger.error("down stream reserve deliver service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.downstreamResponseMsgPluginManagerServiceInit(configList).process();
				resultList.add("down stream response msg plugin manager service to init [OK]");
				logger.info("down stream response msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream response msg plugin manager service to init [FAILS]");
				logger.error("down stream response msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.downstreamDeliverMsgPluginManagerServiceInit(configList).process();
				resultList.add("down stream deliver msg plugin manager service to init [OK]");
				logger.info("down stream deliver msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream deliver msg plugin manager service to init [FAILS]");
				logger.error("down stream deliver msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
		}		
		return resultList;
	}
	public List<String> downstreamService() {
		return downstreamService(null);
	}
	@Override
	public void run() {

	}

	@Override
	public void process() throws Exception {

	}
	public static void main(String[] args) {
		new DefaultClientStartService(GlobalVars.config).duplexstreamService();
	}
}
