package org.duodo.netty3ext.processor;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface Processor extends Runnable {
    public void process() throws Exception;
}
