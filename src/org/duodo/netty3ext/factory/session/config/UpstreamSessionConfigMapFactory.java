package org.duodo.netty3ext.factory.session.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CombinedConfiguration;
import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.config.session.UpstreamSessionConfig;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 * @param <E>
 */
public class UpstreamSessionConfigMapFactory<T extends Map<String, Map<String, E>>, E extends SessionConfig> extends
        SessionConfigMapFactory<T, E> {
	/**
	 * 
	 * @param configuration
	 * @param sessionConfigMap
	 * @param configList
	 */
    @SuppressWarnings("unchecked")
    public UpstreamSessionConfigMapFactory(
            CombinedConfiguration configuration,
            T sessionConfigMap,
            List<String> configList) {
        this(configuration, sessionConfigMap, "upstream", "session", (Class<E>) UpstreamSessionConfig.class, configList);
    }
    /**
     * 
     * @param configuration
     * @param sessionConfigMap
     * @param sessionType
     * @param configName
     * @param classz
     * @param configList
     */
    public UpstreamSessionConfigMapFactory(
            CombinedConfiguration configuration,
            T sessionConfigMap, 
            String sessionType,
            String configName,
            Class<E> classz,
            List<String> configList) {
        super(configuration, sessionConfigMap, sessionType, configName, classz, configList);
    }

}
