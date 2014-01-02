package org.duodo.netty3ext.packet;

/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface Head {
    public DataType getDataType();
    public int getLength();
    public int getHeadLength();
}
