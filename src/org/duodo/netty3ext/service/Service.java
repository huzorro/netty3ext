package org.duodo.netty3ext.service;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface Service extends Runnable {
    public void process() throws Exception;
}
