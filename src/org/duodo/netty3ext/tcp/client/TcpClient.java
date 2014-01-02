package org.duodo.netty3ext.tcp.client;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 */
public interface TcpClient<T> {
    public T connect();
}
