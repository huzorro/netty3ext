/**
 * 
 */
package org.duodo.netty3ext.factory.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.tcp.server.NettyTcpServer;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 * @param <T>
 */
public class NettyTcpServerFactory<T> implements Factory<T> {
    private InetSocketAddress serverAddress;
    private ServerBootstrap bootstrap;
    /**
     * 
     * @param host
     * @param port
     * @param channelPipelineFactory
     * @param bootstrap
     */
    public NettyTcpServerFactory(
            String host, 
            int port, 
            ChannelPipelineFactory channelPipelineFactory,
            ServerBootstrap bootstrap) {
        this(new InetSocketAddress(host, port), 
                channelPipelineFactory,
                bootstrap,
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());
    }
    /**
     * 
     * @param serverAddress
     * @param channelPipelineFactory
     * @param bootstrap
     * @param boss
     * @param work
     */
    public NettyTcpServerFactory(InetSocketAddress serverAddress, 
            ChannelPipelineFactory channelPipelineFactory,
            ServerBootstrap bootstrap,
            ExecutorService boss,
            ExecutorService work) {
        this.serverAddress = serverAddress;
        this.bootstrap = bootstrap;
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, work));
        bootstrap.setPipelineFactory(channelPipelineFactory);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
    }

	@Override
	@SuppressWarnings("unchecked")
	public T create() throws Exception {
		return (T) new NettyTcpServer<T>(bootstrap, serverAddress);
	}

}
