package org.duodo.netty3ext.config.session;

import org.apache.commons.configuration.XMLConfiguration;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface SessionConfig  {
    public String getChannelIds();
    public void setChannelIds(String ids);
    public String getHost();
    public void setHost(String host);
    public void setPort(int port);
    public int getPort();
    public void setUser(String user);
    public String getUser();
    public String getPasswd();
    public void setPasswd(String passwd);
    public void setGateId(int gateId);
    public int getGateId();
    public short getVersion();
    public void setVersion(short version);
    public String getAttPreffix();
    public void setAttPreffix(String attPreffix);
    public XMLConfiguration getConfiguration();
    public void setConfiguration(XMLConfiguration configuration);
    public Object getAttachment();
    public void setAttachment(Object attachment);
    public void setIdleTime(long seconds);
    public long getIdleTime();
    public void setLifeTime(long seconds);
    public long getLifeTime();
    public void setPoolCheckoutTimeout(long milliseconds);
    public long getPoolCheckoutTimeout();
    public void setPoolWatchTime(long seconds);
    public long getPoolWatchTime();    
    public void setMaxRetry(int maxRetry);
    public int getMaxRetry();
    public void setRetryWaitTime(long seconds);
    public long getRetryWaitTime();
    public void setMaxSessions(int maxSessions);
    public int getMaxSessions();
    public void setWindows(int windows);
    public int getWindows();
    public void setGeneralThreadNum(int generalThreadNum);
    public int getGeneralThreadNum();
    public void setScheduleThreadNum(int scheduleThreadNum);
    public int getScheduleThreadNum();
    
    
    public String getReceiveQueueName();
    public void setReceiveQueueName(String queueName);
    public String getResponseQueueName();
    public void setResponseQueueName(String queueName);
    public String getDeliverQueueName();
    public void setDeliverQueueName(String queueName);  
    public void setReserveQueueName(String queueName);
    public String getReserveQueueName();
    
    public void setReceiveQueuePathHome(String pathHome);
    public String getReceiveQueuePathHome();
    public void setResponseQueuePathHome(String pathHome);
    public String getResponseQueuePathHome();
    public void setDeliverQueuePathHome(String pathHome);
    public String getDeliverQueuePathHome();
    public void setReserveQueuePathHome(String pathHome);
    public String getReserveQueuePathName();
}
