package org.duodo.netty3ext.global;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.addpluginsfrom.OptionReportAfter;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import net.xeoh.plugins.base.util.uri.ClassURI;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.session.config.DownstreamSessionConfigMapFactory;
import org.duodo.netty3ext.factory.session.config.DuplexstreamSessionConfigMapFactory;
import org.duodo.netty3ext.factory.session.config.UpstreamSessionConfigMapFactory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.DefaultSessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DefaultGlobalVarsInitialize implements GlobalVarsInitialize {
	protected String configName;
	
    public DefaultGlobalVarsInitialize() {
    	this("session");
	}
    /**
     * 
     * @param configName
     */
    public DefaultGlobalVarsInitialize(String configName) {
		this.configName = configName;
	}


	@Override
	public GlobalVarsInitialize upstreamSessionConfigInitialize(List<String> configList)
			throws Exception {
		new UpstreamSessionConfigMapFactory<Map<String,Map<String,SessionConfig>>, SessionConfig>(GlobalVars.config, GlobalVars.upstreamSessionConfigMap, configList).create();
		return this;
	}
	@Override
	public GlobalVarsInitialize downstreamSessionConfigInitialize(List<String> configList)
			throws Exception {
		new DownstreamSessionConfigMapFactory<Map<String,Map<String,SessionConfig>>, SessionConfig>(GlobalVars.config, GlobalVars.downstreamSessionConfigMap, configList).create();
		return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamSessionConfigInitialize(List<String> configList)
			throws Exception {
		new DuplexstreamSessionConfigMapFactory<Map<String,Map<String,SessionConfig>>, SessionConfig>(GlobalVars.config, GlobalVars.duplexSessionConfigMap, configList).create();
		return this;
	}
	@Override
	public GlobalVarsInitialize upstreamSessionPoolInitialize(List<String> configList) {
    	for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
    		GlobalVars.sessionPoolMap.put(config, new DefaultSessionPool());
    	}
		return this;
	}
	@Override
	public GlobalVarsInitialize downstreamSessionPoolInitialize(List<String> configList) {
    	for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
    		GlobalVars.sessionPoolMap.put(config, new DefaultSessionPool());
    	}
		return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamSessionPoolInitialize(List<String> configList) {
    	for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
    		GlobalVars.sessionPoolMap.put(config, new DefaultSessionPool());
    	}
		return this;
	}
	@Override
	public GlobalVarsInitialize upstreamMessageQueueInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
			GlobalVars.deliverMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getDeliverQueuePathHome(), config
							.getDeliverQueueName()));
			GlobalVars.receiveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReceiveQueuePathHome(), config
							.getReceiveQueueName()));
			GlobalVars.responseMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getResponseQueuePathHome(), config
							.getResponseQueueName()));
			GlobalVars.reserveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReserveQueuePathName(), config
							.getReserveQueueName()));	
        }
        return this;
	}
	@Override
	public GlobalVarsInitialize downstreamMessageQueueInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
			GlobalVars.deliverMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getDeliverQueuePathHome(), config
							.getDeliverQueueName()));
			GlobalVars.receiveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReceiveQueuePathHome(), config
							.getReceiveQueueName()));
			GlobalVars.responseMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getResponseQueuePathHome(), config
							.getResponseQueueName()));
			GlobalVars.reserveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReserveQueuePathName(), config
							.getReserveQueueName()));	
        }
        return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamMessageQueueInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
    		if(configList != null && !configList.contains(config.getChannelIds())) continue;
			GlobalVars.deliverMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getDeliverQueuePathHome(), config
							.getDeliverQueueName()));
			GlobalVars.receiveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReceiveQueuePathHome(), config
							.getReceiveQueueName()));
			GlobalVars.responseMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getResponseQueuePathHome(), config
							.getResponseQueueName()));
			GlobalVars.reserveMsgQueueMap.put(
					config,
					new BdbQueueMap<Long, QFuture<Message>>(config
							.getReserveQueuePathName(), config
							.getReserveQueueName()));			
        }
        return this;
	}

    /* (non-Javadoc)
     * @see me.huzorro.gateway.GlobalVarsInitialize#threadPoolInitialize()
     */
    @Override
    public GlobalVarsInitialize upstreamThreadPoolInitialize() {
    	return upstreamThreadPoolInitialize(null);
    }
    
    public GlobalVarsInitialize upstreamThreadPoolInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.executorServiceMap.put(config, 
                    config.getGeneralThreadNum() > 0 ?
                Executors.newFixedThreadPool(config.getGeneralThreadNum()) : 
                    Executors.newCachedThreadPool());
            
            GlobalVars.scheduleExecutorMap.put(config, 
                    config.getScheduleThreadNum() > 0 ? 
                            Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
                                Executors.newScheduledThreadPool(0));
            GlobalVars.externalScheduleExecutorMap.put(config, config.getScheduleThreadNum() > 0 ?
            				Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
            					Executors.newScheduledThreadPool(1));
        }
        return this;    	
    }
    
    
    @Override
	public GlobalVarsInitialize downstreamThreadPoolInitialize() {
    	return downstreamThreadPoolInitialize(null);
	}
	@Override
	public GlobalVarsInitialize downstreamThreadPoolInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.executorServiceMap.put(config, 
                    config.getGeneralThreadNum() > 0 ?
                Executors.newFixedThreadPool(config.getGeneralThreadNum()) : 
                    Executors.newCachedThreadPool());
            
            GlobalVars.scheduleExecutorMap.put(config, 
                    config.getScheduleThreadNum() > 0 ? 
                            Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
                                Executors.newScheduledThreadPool(0));
            GlobalVars.externalScheduleExecutorMap.put(config, config.getScheduleThreadNum() > 0 ?
    				Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
    					Executors.newScheduledThreadPool(1));            
        }		
        return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamThreadPoolInitialize() {
		return duplexstreamThreadPoolInitialize(null);
	}
	@Override
	public GlobalVarsInitialize duplexstreamThreadPoolInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.executorServiceMap.put(config, 
                    config.getGeneralThreadNum() > 0 ?
                Executors.newFixedThreadPool(config.getGeneralThreadNum()) : 
                    Executors.newCachedThreadPool());
            
            GlobalVars.scheduleExecutorMap.put(config, 
                    config.getScheduleThreadNum() > 0 ? 
                            Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
                                Executors.newScheduledThreadPool(0));
            GlobalVars.externalScheduleExecutorMap.put(config, config.getScheduleThreadNum() > 0 ?
    				Executors.newScheduledThreadPool(config.getScheduleThreadNum()) :
    					Executors.newScheduledThreadPool(1));            
        }   		
		return this;
	}
	/* (non-Javadoc)
     * @see me.huzorro.gateway.GlobalVarsInitialize#bootstrapInitialize()
     */
    @Override
    public GlobalVarsInitialize upstreamClientBootstrapInitialize() {
    	return upstreamClientBootstrapInitialize(null);
    }

    public GlobalVarsInitialize upstreamClientBootstrapInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.clientBootstrapMap.put(config, new ClientBootstrap());
        }
    	return this;
    }
    
    @Override
	public GlobalVarsInitialize downstreamClientBootstrapInitialize() {
    	return downstreamClientBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize downstreamClientBootstrapInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.clientBootstrapMap.put(config, new ClientBootstrap());
        }
		return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamClientBootstrapInitialize() {
		return duplexstreamClientBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize duplexstreamClientBootstrapInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.clientBootstrapMap.put(config, new ClientBootstrap());
        }
        return this;
	}
	/* (non-Javadoc)
     * @see me.huzorro.gateway.GlobalVarsInitialize#serverBootstrapInitialize()
     */
    @Override
    public GlobalVarsInitialize upstreamServerBootstrapInitialize() {
    	return upstreamServerBootstrapInitialize(null);
    }

    public GlobalVarsInitialize upstreamServerBootstrapInitialize(List<String> configList) {
        for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.serverBootstrapMap.put(config, new ServerBootstrap());
        }
        return this;    
    }
	@Override
	public GlobalVarsInitialize downstreamServerBootstrapInitialize() {
		return downstreamServerBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize downstreamServerBootstrapInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.serverBootstrapMap.put(config, new ServerBootstrap());
        }
		return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamServerBootstrapInitialize() {
		return duplexstreamServerBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize duplexstreamServerBootstrapInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
            GlobalVars.serverBootstrapMap.put(config, new ServerBootstrap());
        }
        return this;
	}
	@Override
	public GlobalVarsInitialize upstreamMessagePluginManagerInitialize() {
		return upstreamMessagePluginManagerInitialize(null);
	}
	@Override
	public GlobalVarsInitialize upstreamMessagePluginManagerInitialize(
			List<String> configList) {

        for(SessionConfig config : GlobalVars.upstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
        	PluginManagerUtil pluginManagerUtil = new PluginManagerUtil(PluginManagerFactory.createPluginManager());
        	pluginManagerUtil.addPluginsFrom(ClassURI.CLASSPATH, new OptionReportAfter());
			GlobalVars.pluginManagerUtilMap.put(
					config, 
					pluginManagerUtil);	
        }

        return this;		
	}
	@Override
	public GlobalVarsInitialize downstreamMessagePluginManagerInitialize() {
		return downstreamClientBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize downstreamMessagePluginManagerInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.downstreamSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
        	PluginManagerUtil pluginManagerUtil = new PluginManagerUtil(PluginManagerFactory.createPluginManager());
        	pluginManagerUtil.addPluginsFrom(ClassURI.CLASSPATH, new OptionReportAfter());
			GlobalVars.pluginManagerUtilMap.put(
					config, 
					pluginManagerUtil);	
        }
    	return this;
	}
	@Override
	public GlobalVarsInitialize duplexstreamMessagePluginManagerInitialize() {
		return duplexstreamClientBootstrapInitialize(null);
	}
	@Override
	public GlobalVarsInitialize duplexstreamMessagePluginManagerInitialize(
			List<String> configList) {
        for(SessionConfig config : GlobalVars.duplexSessionConfigMap.get(configName).values()) {
        	if(null != configList && !configList.contains(config.getChannelIds())) continue;
        	PluginManagerUtil pluginManagerUtil = new PluginManagerUtil(PluginManagerFactory.createPluginManager());
        	pluginManagerUtil.addPluginsFrom(ClassURI.CLASSPATH, new OptionReportAfter());
			GlobalVars.pluginManagerUtilMap.put(
					config, 
					pluginManagerUtil);		
        }
		return this;
	}
    
}
