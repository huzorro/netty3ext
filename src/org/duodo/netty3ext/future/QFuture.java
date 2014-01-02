package org.duodo.netty3ext.future;

import java.io.Serializable;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface QFuture<T> extends Serializable {
	public T getMaster();
    public void setSuccess();
    public boolean isSuccess();
    public void addListener(QFutureListener<T> listener);
    public void removeListener(QFutureListener<T> listenner);
}
