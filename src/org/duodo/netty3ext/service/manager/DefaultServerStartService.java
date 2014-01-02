/**
 * 
 */
package org.duodo.netty3ext.service.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DefaultServerStartService implements StartServices {
	private final Logger logger = LoggerFactory.getLogger(DefaultServerStartService.class);
	private CombinedConfiguration  config;
	private List<ServerServices> services = new ArrayList<ServerServices>();
	private String configName;
	private String configKey;
	/**
	 * 
	 * @param config
	 */
	public DefaultServerStartService(CombinedConfiguration  config) {
		this(config, "service", "server.service");
	}
	/**
	 * 
	 * @param config
	 * @param configName
	 * @param configKey
	 */
	public DefaultServerStartService(CombinedConfiguration  config, String configName, String configKey) {
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
				Class<ServerServices> classz = (Class<ServerServices>) Class.forName((String) service);
				services.add(classz.newInstance());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error("start server to init services fails", e.getCause() != null ? e.getCause() : e);
				Runtime.getRuntime().exit(-1);
			} 
		}
	}

	@Override
	public List<String> duplexstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(ServerServices service : services) {
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
				service.duplexstreamReceiveMsgPluginManagerServiceInit(configList).process();
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


	@Override
	public List<String> duplexstreamService() {
		return duplexstreamService(null);
	}


	@Override
	public List<String> upstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for(ServerServices service : services) {
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
				service.upstreamDeliverServiceInit(configList).process();
				resultList.add("up stream deliver service to init [OK]");
				logger.info("up stream deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream deliver service to init [OK]");
				logger.error("up stream deliver service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.upstreamReserveDeliverServiceInit(configList).process();;
				resultList.add("up stream reserve deliver service to init [OK]");
				logger.info("up stream reserve deliver service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream reserve deliver service to init [FAILS]");
				logger.error("up stream reserve deliver service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.upstreamDeliverMsgPluginManagerServiceInit(configList).process();;
				resultList.add("up stream deliver msg plugin manager service to init [OK]");
				logger.info("up stream deliver msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream deliver msg plugin manager service to init [FAILS]");
				logger.error("up stream deliver msg plugin manager service to init [OK]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
			try {
				service.upstreamResponseMsgPluginManagerServiceInit(configList).process();
				resultList.add("up stream response msg plugin manager service to init [OK]");
				logger.info("up stream response msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("up stream response msg plugin manager service to init [FAILS]");
				logger.error("up stream response msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
		}
		return resultList;
	}

	@Override
	public List<String> upstreamService() {
		return upstreamService(null);
	}

	@Override
	public List<String> downstreamService(List<String> configList) {
		List<String> resultList = new ArrayList<String>();
		for (ServerServices service : services) {
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
				service.downstreamReceiveMsgPluginManagerServiceInit(configList).process();
				resultList.add("down stream receive msg plugin manager service to init [OK]");
				logger.info("down stream receive msg plugin manager service to init [OK]");
			} catch (Exception e) {
				resultList.add("down stream receive msg plugin manager service to init [FAILS]"); 
				logger.error("down stream receive msg plugin manager service to init [FAILS]", e.getCause() != null ? e.getCause() : e);
				continue;
			}
		}
		return resultList;
	}

	@Override
	public List<String> downstreamService() {
		return downstreamService(null);
	}
	@Override
	public void process() throws Exception {
	}

	@Override
	public void run() {
	}
}
