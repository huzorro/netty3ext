package org.duodo.netty3ext.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.DefaultFuture;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public  class DefaultSession implements Session {
    private final Logger logger = LoggerFactory.getLogger(DefaultSession.class);
    private Channel channel;
    private Object attachment;
    private ConcurrentMap<Object, QFuture<Message>> requestMap = new ConcurrentHashMap<Object, QFuture<Message>>();
    private BdbQueueMap<Long, QFuture<Message>> responseQueue;
    private BdbQueueMap<Long, QFuture<Message>> receiveQueue;
    private BdbQueueMap<Long, QFuture<Message>> deliverQueue;
    private ConcurrentMap<Object, Future<?>> scheduledTaskMap = new ConcurrentHashMap<Object, Future<?>>();
    private ScheduledExecutorService scheduledExecutor;
    private SessionConfig config;
    private Semaphore windows;
    private QFuture<Session> closeFuture = new DefaultFuture<Session>(this);
    private QFuture<Session> loginFuture = new DefaultFuture<Session>(this);
    /**
     * 
     * @param channel
     * @param deliverQueue
     * @param responseQueue
     * @param receiveQueue
     * @param scheduleExecutor
     * @param config
     */
    public DefaultSession(Channel channel,
    		BdbQueueMap<Long, QFuture<Message>> deliverQueue,
    		BdbQueueMap<Long, QFuture<Message>> responseQueue,
    		BdbQueueMap<Long, QFuture<Message>> receiveQueue,
            ScheduledExecutorService scheduleExecutor,
            SessionConfig config) {
        setChannel(channel);
        setConfig(config);
        this.responseQueue = responseQueue;
        this.receiveQueue = receiveQueue;
        this.deliverQueue = deliverQueue;
        this.scheduledExecutor = scheduleExecutor;        
        windows = new Semaphore(this.config.getWindows());
    }
    /**
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
        this.channel.getCloseFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    closeFuture.setSuccess();
                    logger.info("receive channelCloseFuture to channel close {}", future.getChannel());
                    for(QFuture<Message> futureOfMessage : requestMap.values()) {
                        deliverQueue.put(futureOfMessage);
                    }
                    for(Future<?> futureOfTask : scheduledTaskMap.values()) {
                        futureOfTask.cancel(true);
                    }
                }
            }
        });
        this.channel.setAttachment(this);
    }

	@Override
    public void deliver(QFuture<Message> messageFuture) throws InterruptedException {
        windows.acquire();
        messageFuture.getMaster().incrementAndGetRequests(config.getMaxRetry());       
        if(messageFuture.getMaster().getRequests() 
        		> config.getMaxRetry()) {
            logger.info("The request to send a message number has reached the maximum limit {}", messageFuture.getMaster().getRequests());
            logger.info("session closing");
            close();
            return;
        }
        requestMap.put(messageFuture.getMaster().getHeader().getSequenceId().toString(), messageFuture);
        channel.write(messageFuture.getMaster());
    }
    @Override
    public void response(Message message) throws InterruptedException {
        QFuture<Message> requestFuture = requestMap.remove(message.getHeader().getSequenceId().toString());
        QFuture<Message> responseFuture = new DefaultFuture<Message>(requestFuture.getMaster().setResponse(message));
        responseQueue.put(responseFuture); 
        requestFuture.setSuccess();
        windows.release();
    }
    @Override
    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
     @Override
    public Object getAttachment() {
        return attachment;
    }
    
     @Override
    public void deliverAndScheduleTask(final QFuture<Message> messageFuture) throws InterruptedException {
        windows.acquire();
        messageFuture.getMaster().incrementAndGetRequests(config.getMaxRetry());
        Future<?> future = scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                	deliver(messageFuture);
                } catch (Exception e) {
                    logger.error("windows acquire interrupted: ", e.getCause() != null ? e.getCause() : e);
                    try {
                        close();
                    } catch (InterruptedException e1) {
                        logger.error("session close interrupted: ", e.getCause() != null ? e.getCause() : e);
                    }
                }
            }
        } , config.getRetryWaitTime(), config.getRetryWaitTime(), TimeUnit.SECONDS);
        
        scheduledTaskMap.put(messageFuture.getMaster().getHeader().getSequenceId().toString(), future);
        requestMap.put(messageFuture.getMaster().getHeader().getSequenceId().toString(), messageFuture);
        channel.write(messageFuture.getMaster());
    }
     @Override
    public void responseAndScheduleTask(Message message) throws InterruptedException {
        scheduledTaskMap.remove(message.getHeader().getSequenceId().toString()).cancel(true);
        QFuture<Message> requestFuture = requestMap.remove(message.getHeader().getSequenceId().toString());
        QFuture<Message> responseFuture = new DefaultFuture<Message>(requestFuture.getMaster().setResponse(message));
        responseQueue.put(responseFuture); 
        requestFuture.setSuccess();
        windows.release();
    }
    @Override
    public void receive(Message message) throws Exception {
        QFuture<Message> messageFuture = new DefaultFuture<Message>(message);
        receiveQueue.put(messageFuture);
    }    
    @Override
    public void close() throws InterruptedException {
        channel.close();
    }
    @Override
    public SessionConfig getConfig() {
        return config;
    }
    @Override
    public void setConfig(SessionConfig config) {
        this.config = config;
    }
    @Override
    public boolean isClosed() {
        return closeFuture.isSuccess();
    }
     @Override
    public QFuture<Session> getCloseFuture() {
        return closeFuture;
    }
     @Override
    public QFuture<Session> getLoginFuture() {
        return loginFuture;
    }
	@Override
	public boolean isWindowFull() {
		return requestMap.size() >= config.getWindows();
	}
    
}
