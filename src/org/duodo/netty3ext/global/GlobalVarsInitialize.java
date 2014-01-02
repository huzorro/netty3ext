package org.duodo.netty3ext.global;

import java.util.List;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface GlobalVarsInitialize {

    public GlobalVarsInitialize upstreamSessionConfigInitialize(List<String> configList) throws Exception;
    
    public GlobalVarsInitialize downstreamSessionConfigInitialize(List<String> configList) throws Exception;

    public GlobalVarsInitialize duplexstreamSessionConfigInitialize(List<String> configList) throws Exception;
    
    public GlobalVarsInitialize upstreamSessionPoolInitialize(List<String> configList); 
    
    public GlobalVarsInitialize downstreamSessionPoolInitialize(List<String> configList); 

    public GlobalVarsInitialize duplexstreamSessionPoolInitialize(List<String> configList); 
    
    public GlobalVarsInitialize upstreamMessageQueueInitialize(List<String> configList); 
    
    public GlobalVarsInitialize downstreamMessageQueueInitialize(List<String> configList); 

    public GlobalVarsInitialize duplexstreamMessageQueueInitialize(List<String> configList); 
    
    public GlobalVarsInitialize upstreamMessagePluginManagerInitialize();

    public GlobalVarsInitialize upstreamMessagePluginManagerInitialize(List<String> configlList);
    
    public GlobalVarsInitialize downstreamMessagePluginManagerInitialize();

    public GlobalVarsInitialize downstreamMessagePluginManagerInitialize(List<String> configList);
    
    public GlobalVarsInitialize duplexstreamMessagePluginManagerInitialize();

    public GlobalVarsInitialize duplexstreamMessagePluginManagerInitialize(List<String> configList);
        
    public GlobalVarsInitialize upstreamThreadPoolInitialize(); 
    
    public GlobalVarsInitialize upstreamThreadPoolInitialize(List<String> configList);
   
    public GlobalVarsInitialize downstreamThreadPoolInitialize();
    
    public GlobalVarsInitialize downstreamThreadPoolInitialize(List<String> configList);
    
    public GlobalVarsInitialize duplexstreamThreadPoolInitialize();
    
    public GlobalVarsInitialize duplexstreamThreadPoolInitialize(List<String> configList);
    
    public GlobalVarsInitialize upstreamClientBootstrapInitialize();
    
    public GlobalVarsInitialize upstreamClientBootstrapInitialize(List<String> configList);
    
    public GlobalVarsInitialize downstreamClientBootstrapInitialize();

    public GlobalVarsInitialize downstreamClientBootstrapInitialize(List<String> configList);
    
    public GlobalVarsInitialize duplexstreamClientBootstrapInitialize();

    public GlobalVarsInitialize duplexstreamClientBootstrapInitialize(List<String> configList);
    
    public GlobalVarsInitialize upstreamServerBootstrapInitialize();

    public GlobalVarsInitialize upstreamServerBootstrapInitialize(List<String> configList);

    public GlobalVarsInitialize downstreamServerBootstrapInitialize(); 

    public GlobalVarsInitialize downstreamServerBootstrapInitialize(List<String> configList); 
    
    public GlobalVarsInitialize duplexstreamServerBootstrapInitialize();

    public GlobalVarsInitialize duplexstreamServerBootstrapInitialize(List<String> configList);
}
