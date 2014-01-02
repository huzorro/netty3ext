package org.duodo.netty3ext.service;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.processor.Processor;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 */
public class MessageBindToChannelSubmitService<T extends Processor> implements Service {
    private static final Logger logger = LoggerFactory.getLogger(MessageBindToChannelSubmitService.class);
    private Map<String, SessionConfig> configMap;
    private Map<SessionConfig, SessionPool> sessionPoolMap;
    private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
    private Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap;
    private Map<SessionConfig, ExecutorService> executorServiceMap;
    private boolean isNeedPoolCheckoutTimeout;
    private Class<T> requestSubmitProcessor;
    private List<String> configList;
    /**
     * 
     * @param sessionPoolMap
     * @param deliverMsgQueueMap
     * @param reserveMsgQueueMap
     * @param executorServiceMap
     * @param configMap
     * @param isNeedPoolCheckoutTimeout
     * @param requestSubmitProcessor
     * @param configList
     */
	public MessageBindToChannelSubmitService(            
			Map<SessionConfig, SessionPool> sessionPoolMap,
            Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
            Map<Object, BdbQueueMap<Long, QFuture<Message>>> reserveMsgQueueMap,
            Map<SessionConfig, ExecutorService> executorServiceMap, 
            Map<String, SessionConfig> configMap,
            boolean isNeedPoolCheckoutTimeout,
            Class<T> requestSubmitProcessor,
            List<String> configList) {
        this.configMap = configMap;
        this.sessionPoolMap = sessionPoolMap;
        this.deliverMsgQueueMap = deliverMsgQueueMap;
        this.reserveMsgQueueMap = reserveMsgQueueMap;
        this.executorServiceMap = executorServiceMap;
        this.isNeedPoolCheckoutTimeout = isNeedPoolCheckoutTimeout;
        this.requestSubmitProcessor = requestSubmitProcessor;
        this.configList = configList;
	}

	@Override
	public void run() {
        try {
            process();
        } catch (Exception e) {
            logger.error("submit the request processing failed", e.getCause() != null ? e.getCause() : e);
        }
	}

	@Override
	public void process() throws Exception {
        for(final SessionConfig config : configMap.values()) {
        	if(configList != null && !configList.contains(config.getChannelIds())) continue;
        	create(config, sessionPoolMap.get(config));
        }		
	}
	public void create(final SessionConfig config, final SessionPool pool) throws Exception{
    	final BdbQueueMap<Long, QFuture<Message>> deliverQueue = deliverMsgQueueMap.get(config);
    	final BdbQueueMap<Long, QFuture<Message>> reserveQueue = reserveMsgQueueMap.get(config);
        executorServiceMap.get(config).execute(new Runnable() {
			@Override
			@SuppressWarnings("unchecked")
            public void run() {
				try {
					while (true) {
						if (Thread.currentThread().isInterrupted())
							break;
						Thread.sleep(0);
						Session session = null;
						QFuture<Message> messageFuture = null;
						try {
							messageFuture = deliverQueue.take();
							session = pool.checkout(
									messageFuture.getMaster().getChannelIds(),
									isNeedPoolCheckoutTimeout ? config
											.getPoolCheckoutTimeout() : 0,
									TimeUnit.MILLISECONDS);
							Constructor<?> constructor = requestSubmitProcessor
									.getConstructor(BdbQueueMap.class,
											SessionPool.class, Session.class,
											QFuture.class);
							T processor = (T) constructor.newInstance(
									reserveQueue, pool, session, messageFuture);
							executorServiceMap.get(config).execute(processor);
						} catch (Exception e) {
							if(messageFuture != null) reserveQueue.put(messageFuture);
							try {
								session.close();
							} catch (Exception e1) {
								logger.error("close the session fails", e.getCause() != null ? e.getCause() : e );
							}
							if(e instanceof InterruptedException) {
								logger.error("currentThread interrupted", e.getCause() != null ? e.getCause() : e);
								break;
							}
							logger.error("take session fails", e.getCause() != null ? e.getCause() : e);
						}
					}
				} catch (InterruptedException e) {
					logger.error(
							"currentThread interrupted put messageFutrue to reserveQueue fails",
							e.getCause() != null ? e.getCause() : e);
				}
			}
        });		
	}

}
