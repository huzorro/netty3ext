package org.duodo.netty3ext.packet;


/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public interface PacketType {    
    public long getCommandId();
    public PacketStructure[] getPacketStructures();
    public long getAllCommandId();
}
