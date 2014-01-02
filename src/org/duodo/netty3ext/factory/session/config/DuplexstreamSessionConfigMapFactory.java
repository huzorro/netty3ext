package org.duodo.netty3ext.factory.session.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CombinedConfiguration;
import org.duodo.netty3ext.config.session.DuplexstreamSessionConfig;
import org.duodo.netty3ext.config.session.SessionConfig;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DuplexstreamSessionConfigMapFactory<T extends Map<String, Map<String, E>>, E extends SessionConfig> extends
        SessionConfigMapFactory<T, E> {
	/**
	 * 
	 * @param configurationBuilder
	 * @param sessionConfigMap
	 * @param configList
	 */
    @SuppressWarnings("unchecked")
    public DuplexstreamSessionConfigMapFactory(
            CombinedConfiguration configurationBuilder,
            T sessionConfigMap,
            List<String> configList) {
        this(configurationBuilder, sessionConfigMap, "duplexstream", "session", (Class<E>) DuplexstreamSessionConfig.class, configList);
    }
    /**
     * @param configurationBuilder
     * @param sessionConfigMap
     * @param sessionType
     * @param sessionConfig
     */
    public DuplexstreamSessionConfigMapFactory(
            CombinedConfiguration configurationBuilder,
            T sessionConfigMap, 
            String sessionType,
            String configName,
            Class<E> sessionConfig,
            List<String> configList) {
        super(configurationBuilder, sessionConfigMap, sessionType, configName, sessionConfig, configList);
    }

}
