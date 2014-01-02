package org.duodo.netty3ext.factory.session.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 * @param <E>
 */
public class SessionConfigMapFactory<T extends Map<String, Map<String, E>>, E extends SessionConfig> implements Factory<T> {
    private final Logger logger = LoggerFactory.getLogger(SessionConfigMapFactory.class);
    private CombinedConfiguration  config;
    private T sessionConfigMap;
    private String sessionType;
    private String configName;
    private Class<E> sessionConfig;
    private List<String> configList;
    /**
     * 
     * @param config
     * @param sessionConfigMap
     * @param sessionType
     * @param configName
     * @param sessionConfig
     * @param configList
     */
    public SessionConfigMapFactory(
            CombinedConfiguration config,
            T sessionConfigMap,
            String sessionType,
            String configName,
            Class<E> sessionConfig,
            List<String> configList) {
        this.config = config;
        this.sessionConfigMap = sessionConfigMap;
        this.sessionType = sessionType;
        this.configName = configName;
        this.sessionConfig = sessionConfig;
        this.configList = configList;
    }


    @Override
    public T  create() throws ConfigurationException, InstantiationException, IllegalAccessException{
        XMLConfiguration xmlConfig = (XMLConfiguration) config.getConfiguration(configName);
        xmlConfig.addConfigurationListener(new ConfigurationListener() {
            @Override
            public void configurationChanged(ConfigurationEvent event) {
                if(!event.isBeforeUpdate()) {                   
                    logger.info("{}", "session config changed");
                }
            }
        });
        HierarchicalConfiguration sub = xmlConfig.configurationAt("sessions");
        List<HierarchicalConfiguration>  subList= sub.configurationsAt("session");
        Map<String, E> configMap = new HashMap<String, E>();
        for(int i = 0; i < subList.size(); i++) {            
            E config = sessionConfig.newInstance();
            config.setAttPreffix(String.format("sessions.session(%1$d).%2$s", i, sessionType));
            config.setConfiguration(xmlConfig);
            config.setChannelIds(subList.get(i).getString("channelIds"));
            if(configList != null && !configList.contains(config.getChannelIds())) continue;
            configMap.put(config.getChannelIds(), config);
        }
        
        if(configMap.isEmpty()) throw new InstantiationException("Not found channelIds");

        if(sessionConfigMap.get(configName) != null) {
        	sessionConfigMap.get(configName).putAll(configMap);
        } else {
        	sessionConfigMap.put(configName, configMap);
        }
        return (T) sessionConfigMap;
    }
}
