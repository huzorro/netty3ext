package org.duodo.netty3ext.message;

import java.util.concurrent.atomic.AtomicInteger;

import org.duodo.netty3ext.packet.PacketType;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DefaultMessage implements Message {
    private static final long serialVersionUID = -4245789758843785127L;
    private PacketType packetType;
    private long timestamp = System.currentTimeMillis();
    private String channelIds;
    private String childChannelIds;
    private long lifeTime = 72 * 3600L;
    private AtomicInteger requests = new AtomicInteger();
    private Message response;
    private Message request;
    private Header header;
    private byte[] buffer;
    private Object attachment;
    public DefaultMessage() {
    }
	@Override
	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}
	@Override
	public PacketType getPacketType() {
		return packetType;
	}    
	public void setTimestamp(long milliseconds) {
		this.timestamp = milliseconds;
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
		
	}
	@Override
	public String getChannelIds() {
		return channelIds;
	}
	@Override
	public void setChildChannelIds(String childChannelIds) {
		this.childChannelIds = childChannelIds;
	}
	@Override
	public String getChildChannelIds() {
		return childChannelIds;
	}
	@Override
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	@Override
	public long getLifeTime() {
		return lifeTime;
	}	
 	@Override
	public boolean isTerminationLife() {
		return (System.currentTimeMillis() - timestamp) > (lifeTime * 1000);
	}

    @Override
    public int incrementAndGetRequests(int rN) {
        return requests.compareAndSet(++rN, 0) ? requests.incrementAndGet() : requests.incrementAndGet();
    }
    @Override
    public Message setResponse(Message message) {
        this.response = message;
        return this;
        
    }
    @Override
    public Message getResponse() {
        return response;
    }
	@Override
	public Message setRequest(Message message) {
		this.request = message;
		return this;
	}
	@Override
	public Message getRequest() {
		return request;
	}    
    @Override
    public int getRequests() {
        return requests.get();
    }
    @Override
    public void setHeader(Header header) {
        this.header = header;
    }
     @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setBodyBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
     @Override
    public byte[] getBodyBuffer() {
        return buffer;
    }
    @Override
    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
    @Override
    public Object getAttachment() {
        return attachment;
    }

}
