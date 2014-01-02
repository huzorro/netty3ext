/**
 * 
 */
package org.duodo.netty3ext.factory.session;

import java.util.concurrent.ScheduledExecutorService;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.future.QFutureListener;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.session.DefaultSession;
import org.duodo.netty3ext.session.Session;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzorro(huzorro@gmail.com)
 * @param <T>
 *
 */
public class DefaultServerSessionFactory<T> implements Factory<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServerSessionFactory.class);
    private BdbQueueMap<Long, QFuture<Message>> receiveQueue;
    private BdbQueueMap<Long, QFuture<Message>> responseQueue;
    private BdbQueueMap<Long, QFuture<Message>> deliverQueue;
    private ScheduledExecutorService scheduleExecutor;
    private SessionPool sessionPool;
    private SessionConfig config;
    private Channel channel;
    /**
     * 
     * @param receiveQueue
     * @param responseQueue
     * @param deliverQueue
     * @param scheduleExecutor
     * @param sessionPool
     */
	public DefaultServerSessionFactory(            
			BdbQueueMap<Long, QFuture<Message>> receiveQueue,
			BdbQueueMap<Long, QFuture<Message>> responseQueue,
			BdbQueueMap<Long, QFuture<Message>>deliverQueue,
            ScheduledExecutorService scheduleExecutor,
            SessionPool sessionPool
            ) {
        this.receiveQueue = receiveQueue;
        this.responseQueue = responseQueue;
        this.deliverQueue = deliverQueue;
        this.scheduleExecutor = scheduleExecutor;
        this.sessionPool = sessionPool;
	}    
	/**
	 * 
	 * @param config
	 * @param receiveQueue
	 * @param responseQueue
	 * @param deliverQueue
	 * @param scheduleExecutor
	 * @param sessionPool
	 * @param channel
	 */
	public DefaultServerSessionFactory(            
            SessionConfig config,
            BdbQueueMap<Long, QFuture<Message>> receiveQueue,
            BdbQueueMap<Long, QFuture<Message>> responseQueue,
            BdbQueueMap<Long, QFuture<Message>> deliverQueue,
            ScheduledExecutorService scheduleExecutor,
            SessionPool sessionPool,
            Channel channel
            ) {
        this.config = config;
        this.receiveQueue = receiveQueue;
        this.responseQueue = responseQueue;
        this.deliverQueue = deliverQueue;
        this.scheduleExecutor = scheduleExecutor;
        this.sessionPool = sessionPool;
        this.channel = channel;
	}
	
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	/**
	 * @param config the config to set
	 */
	public void setConfig(SessionConfig config) {
		this.config = config;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T create() throws Exception {
        final DefaultSession session = new DefaultSession(channel, 
                deliverQueue, responseQueue, receiveQueue, scheduleExecutor, config);
        session.getLoginFuture().addListener(new QFutureListener<Session>() {
            
            @Override
            public void onComplete(QFuture<Session> future) {
                if(future.isSuccess())
                    try {
                        sessionPool.put(future.getMaster(), true);
                        logger.info("Channel created successfully {}", session.getChannel());
                    } catch (Exception e) {
                        logger.error("put session to session pool failed", e.getCause() != null ? e.getCause() : e);
                    }
            }
        });
		if (sessionPool.size(config.getChannelIds()) >= config.getMaxSessions()) return null;
		return (T) session;
	}

}
