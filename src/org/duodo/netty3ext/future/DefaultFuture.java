package org.duodo.netty3ext.future;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DefaultFuture<T> implements QFuture<T> {
	private static final long serialVersionUID = 565093414478321808L;
	private T master;
    private Queue<QFutureListener<T>> listeners = new ConcurrentLinkedQueue<QFutureListener<T>>();
    private AtomicBoolean done = new AtomicBoolean(false);
    
    /**
     * 
     * @param master
     */
    public DefaultFuture(T master) {
    	this.master = master;
    }

    public T getMaster() {
    	return master;
    }
    @Override
    public void setSuccess() {
        done.compareAndSet(false, true);
        for(QFutureListener<T> listener : listeners) {
            listener.onComplete(this);
        }
    }

      @Override
    public boolean isSuccess() {
        return done.get();
    }

     @Override
    public void addListener(QFutureListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(QFutureListener<T> listenner) {
        listeners.remove(listenner);
    }

}
