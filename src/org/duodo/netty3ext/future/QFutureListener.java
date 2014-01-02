package org.duodo.netty3ext.future;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface QFutureListener<T> {
    public void onComplete(QFuture<T> future);
}
