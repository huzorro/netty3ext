package org.duodo.netty3ext.tcp.client;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 * @param <T>
 * @param <T>
 */
public class NettyTcpClient<T extends ChannelFuture> implements TcpClient<T> {
    private ClientBootstrap bootstrap;
    private InetSocketAddress serverAddress;
    
    public NettyTcpClient(String host, int port, ClientBootstrap bootstrap) {
        this(bootstrap, new InetSocketAddress(host, port));
    }
    /**
     * 
     * @param bootstrap
     * @param serverAddress
     */
    public NettyTcpClient(ClientBootstrap bootstrap, InetSocketAddress serverAddress) {
        this.bootstrap = bootstrap;
        this.serverAddress = serverAddress;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T connect() {
        return (T) bootstrap.connect(serverAddress);
    }
    /**
     * @return the bootstrap
     */
    public ClientBootstrap getBootstrap() {
        return bootstrap;
    }
    /**
     * @param bootstrap the bootstrap to set
     */
    public void setBootstrap(ClientBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }
    
}
