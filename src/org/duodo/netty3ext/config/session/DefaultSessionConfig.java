package org.duodo.netty3ext.config.session;

import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DefaultSessionConfig implements SessionConfig {
    
    private String channelIds;
    private String attPreffix;
    private XMLConfiguration configuration;
    private Object attachment; 
    @Override
    public String getChannelIds() {
        return channelIds;
    }
    @Override
    public void setChannelIds(String ids) {
        this.channelIds = ids;
    }
	@Override
	public void setUser(String user) {
		
	}
	@Override
	public void setPasswd(String passwd) {
		
	}
	@Override
	public void setVersion(short version) {
		
	}    
    @Override
    public String getHost() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "host"));
    }
    @Override
    public void setHost(String host) {
    }
    @Override
    public void setPort(int port) {
    }

    @Override
    public int getPort() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "port"));
    } 
    
    @Override
    public String getUser() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "user"));
    }
    @Override
    public String getPasswd() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "passwd"));
    }   
    
	@Override
	public void setGateId(int gateId) {
	}
	@Override
	public int getGateId() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "gateId"));
	}
    @Override
    public short getVersion() {
        return configuration.getShort(String.format("%1$s.%2$s", attPreffix, "version"));
    }    
    @Override
    public String getAttPreffix() {
        return attPreffix;
    }
     @Override
    public void setAttPreffix(String attPreffix) {
        this.attPreffix = attPreffix;
    }
     @Override
    public XMLConfiguration getConfiguration() {
        return this.configuration;
    }
     @Override
    public void setConfiguration(XMLConfiguration configuration) {
        this.configuration = configuration;
    }
    @Override
    public Object getAttachment() {
        return attachment;
    }
    @Override
    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
    
	@Override
	public void setIdleTime(long seconds) {
	}
	
	@Override
	public long getIdleTime() {
		return configuration.getLong(String.format("%1$s.%2$s", attPreffix, "idleTime"));
	}    
	
	public void setLifeTime(long seconds) {
	}
	public long getLifeTime() {
		return configuration.getLong(String.format("%1$s.%2$s", attPreffix, "lifeTime"));
	}
	
	@Override
	public void setPoolCheckoutTimeout(long milliseconds) {
	}
	@Override
	public long getPoolCheckoutTimeout() {
        return configuration.getLong(String.format("%1$s.%2$s", attPreffix, "poolCheckoutTimeout"));
	}
	@Override
	public void setPoolWatchTime(long seconds) {
	}
	@Override
	public long getPoolWatchTime() {
        return configuration.getLong(String.format("%1$s.%2$s", attPreffix, "poolWatchTime"));
	}
	
    @Override
    public void setMaxRetry(int maxRetry) {
    }
    @Override
    public int getMaxRetry() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "maxRetry")); 
    }
    @Override
    public void setRetryWaitTime(long seconds) {
    }
    @Override
    public long getRetryWaitTime() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "retryWaitTime"));
    }
    @Override
    public void setMaxSessions(int maxSessions) {
    }
     @Override
    public int getMaxSessions() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "maxSessions"));
    }
    @Override
    public void setWindows(int windows) {
    }
    @Override
    public int getWindows() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "windows"));
    }
    @Override
    public void setGeneralThreadNum(int generalThreadNum) {
    }
    @Override
    public int getGeneralThreadNum() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "generalThreadNum"));
    }
    @Override
    public void setScheduleThreadNum(int scheduleThreadNum) {
    }
    @Override
    public int getScheduleThreadNum() {
        return configuration.getInt(String.format("%1$s.%2$s", attPreffix, "scheduleThreadNum"));
    }
    @Override
    public String  getReceiveQueueName() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "receiveQueueName"));
    }
    @Override
    public void setReceiveQueueName(String queueName) {
    	
    }
    @Override
    public String getResponseQueueName() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "responseQueueName"));
    }
    @Override
    public void setResponseQueueName(String queueName) {
    }
    @Override
    public String getDeliverQueueName() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "deliverQueueName"));
    }
    @Override
    public void setDeliverQueueName(String queueName) {
    }
	@Override
	public void setReserveQueueName(String queueName) {
	}
	@Override
	public String getReserveQueueName() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "reserveQueueName"));
	}
	
    @Override
    public void setReceiveQueuePathHome(String pathHome) {
    }
    @Override
    public String getReceiveQueuePathHome() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "receiveQueuePathHome"));
    }
    @Override
    public void setResponseQueuePathHome(String pathHome) {        
    }
    @Override
    public String getResponseQueuePathHome() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "responseQueuePathHome"));
    }
    @Override
    public void setDeliverQueuePathHome(String pathHome) {        
    }
    @Override
    public String getDeliverQueuePathHome() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "deliverQueuePathHome"));
    }

	@Override
	public void setReserveQueuePathHome(String pathHome) {
	}
	@Override
	public String getReserveQueuePathName() {
        return configuration.getString(String.format("%1$s.%2$s", attPreffix, "reserveQueuePathHome"));
	}
	@Override
	public String toString() {
		return String
				.format("DefaultSessionConfig [getChannelIds()=%s, getHost()=%s, getPort()=%s, getUser()=%s, getPasswd()=%s, getGateId()=%s, getVersion()=%s, getAttPreffix()=%s, getConfiguration()=%s, getAttachment()=%s, getIdleTime()=%s, getLifeTime()=%s, getPoolCheckoutTimeout()=%s, getPoolWatchTime()=%s, getMaxRetry()=%s, getRetryWaitTime()=%s, getMaxSessions()=%s, getWindows()=%s, getGeneralThreadNum()=%s, getScheduleThreadNum()=%s, getReceiveQueueName()=%s, getResponseQueueName()=%s, getDeliverQueueName()=%s, getReserveQueueName()=%s, getReceiveQueuePathHome()=%s, getResponseQueuePathHome()=%s, getDeliverQueuePathHome()=%s, getReserveQueuePathName()=%s]",
						getChannelIds(), getHost(), getPort(), getUser(),
						getPasswd(), getGateId(), getVersion(),
						getAttPreffix(), getConfiguration(), getAttachment(),
						getIdleTime(), getLifeTime(), getPoolCheckoutTimeout(),
						getPoolWatchTime(), getMaxRetry(), getRetryWaitTime(),
						getMaxSessions(), getWindows(), getGeneralThreadNum(),
						getScheduleThreadNum(), getReceiveQueueName(),
						getResponseQueueName(), getDeliverQueueName(),
						getReserveQueueName(), getReceiveQueuePathHome(),
						getResponseQueuePathHome(), getDeliverQueuePathHome(),
						getReserveQueuePathName());
	}

}
