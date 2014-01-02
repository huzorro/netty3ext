package org.duodo.netty3ext.session;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;


/**
 * 
 * @author huzorro(huzorro@gmail.com)
 *
 */
public interface Session {
    public SessionConfig getConfig();
    public void setConfig(SessionConfig config);
    public void deliver(QFuture<Message> messageFuture) throws Exception;
    public void response(Message message) throws Exception;
    public void deliverAndScheduleTask(QFuture<Message> messageFuture) throws Exception;
    public void responseAndScheduleTask(Message message) throws Exception;
    public void receive(Message message) throws Exception;
    public void setAttachment(Object attachment);
    public Object getAttachment();
    public void close() throws Exception;
    public boolean isClosed();
    public boolean isWindowFull();
    public QFuture<Session> getCloseFuture();
    public QFuture<Session> getLoginFuture();
}
