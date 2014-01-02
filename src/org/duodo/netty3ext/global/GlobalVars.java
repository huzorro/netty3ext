package org.duodo.netty3ext.global;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.session.Session;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class GlobalVars {
    private final static Logger logger = LoggerFactory.getLogger(GlobalVars.class);
    public static DefaultConfigurationBuilder configBuilder;
    public static CombinedConfiguration config;
    public static AtomicLong sequenceId = new AtomicLong();
    public static Charset defaultLocalCharset = Charset.forName("UTF-8");
    public static Charset defaultTransportCharset = Charset.forName("GBK");    
    public static Map<String, Map<String, SessionConfig>> duplexSessionConfigMap = new HashMap<String, Map<String, SessionConfig>>();
    public static Map<String, Map<String, SessionConfig>> upstreamSessionConfigMap = new HashMap<String, Map<String, SessionConfig>>();
    public static Map<String, Map<String, SessionConfig>> downstreamSessionConfigMap = new HashMap<String, Map<String, SessionConfig>>();
    
    public static List<SessionConfig> duplexstreamServicesRunningList = new ArrayList<SessionConfig>();
    public static List<SessionConfig> upstreamServicesRunningList = new ArrayList<SessionConfig>();
    public static List<SessionConfig> downstreamServicesRunningList = new ArrayList<SessionConfig>();
    
    public static Map<SessionConfig, SessionPool> sessionPoolMap = new HashMap<SessionConfig, SessionPool>();
    public static Map<SessionConfig, Factory<Session>> sessionFactoryMap = new HashMap<SessionConfig, Factory<Session>>();
    public static Map<SessionConfig, PluginManagerUtil> pluginManagerUtilMap = new HashMap<SessionConfig, PluginManagerUtil>();
    
    public static Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap = 
    		new HashMap<Object, BdbQueueMap<Long, QFuture<Message>>>();
    
    
    public static Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap = 
    		new HashMap<Object, BdbQueueMap<Long, QFuture<Message>>>();
    
    
    public static Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap = 
    		new HashMap<Object, BdbQueueMap<Long, QFuture<Message>>>();

    public static Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap = 
    		new HashMap<Object, BdbQueueMap<Long, QFuture<Message>>>();
    
    public static Map<SessionConfig, ExecutorService> executorServiceMap = new HashMap<SessionConfig, ExecutorService>();
    public static Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap = new HashMap<SessionConfig, ScheduledExecutorService>();
    public static Map<SessionConfig, ScheduledExecutorService> externalScheduleExecutorMap = new HashMap<SessionConfig, ScheduledExecutorService>();
    public static Map<SessionConfig, ClientBootstrap> clientBootstrapMap = new HashMap<SessionConfig, ClientBootstrap>();
    public static Map<SessionConfig, ServerBootstrap> serverBootstrapMap = new HashMap<SessionConfig, ServerBootstrap>();
    
    static {
        configBuilder = new DefaultConfigurationBuilder();
        configBuilder.setFileName("configuration.xml");
        try {
            config = configBuilder.getConfiguration(true);
        } catch (ConfigurationException e) {
            logger.error("config builder failed {}", e.getCause());
        }
    }
}
