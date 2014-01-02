/**
 * 
 */
package org.duodo.netty3ext.tcp.server;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 */
public class NettyTcpServer<T> implements TcpServer<T> {

    private ServerBootstrap bootstrap;
    private InetSocketAddress serverAddress;
    /**
     * 
     * @param host
     * @param port
     * @param bootstrap
     */
    public NettyTcpServer(String host, int port, ServerBootstrap bootstrap) {
        this(bootstrap, new InetSocketAddress(host, port));
    }
    /**
     * 
     * @param bootstrap
     * @param serverAddress
     */
    public NettyTcpServer(ServerBootstrap bootstrap, InetSocketAddress serverAddress) {
        this.bootstrap = bootstrap;
        this.serverAddress = serverAddress;
    }

	@Override
	@SuppressWarnings("unchecked")
	public T bind() {
		return (T) bootstrap.bindAsync(serverAddress).getChannel();
	}

}
