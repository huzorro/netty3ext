package org.duodo.netty3ext.message;

import org.apache.commons.codec.binary.Hex;


/**
 *
 * @author huzorro(huzorro@gmail.com)
 */
public class DefaultHead implements Header {
	private static final long serialVersionUID = -3059342529838994125L;
	private long headLength;
    private long packetLength;
    private long bodyLength;
    private Object commandId;
    private Object sequenceId;
    private byte[] headBuffer;
    
    public DefaultHead() {
    }

    @Override
    public void setHeadLength(long length) {
        this.headLength = length;
    }

    @Override
    public long getHeadLength() {
        return headLength;
    }

    @Override
    public void setPacketLength(long length) {
        this.packetLength = length;
    }

    @Override
    public long getPacketLength() {
        return packetLength;
    }

    @Override
    public void setBodyLength(long length) {
        this.bodyLength = length;
    }

    @Override
    public long getBodyLength() {
        return bodyLength;
    }

     @Override
    public void setCommandId(Object commandId) {
        this.commandId = commandId;
    }

     @Override
    public Object getCommandId() {
        return commandId;
    }


    @Override
    public void setSequenceId(Object transitionId) {
        this.sequenceId = transitionId;
    }

    @Override
    public Object getSequenceId() {
        return sequenceId;
    }

    @Override
    public void setHeadBuffer(byte[] buffer) {
        this.headBuffer = buffer;
    }

    @Override
    public byte[] getHeadBuffer() {
        return headBuffer;
    }

	@Override
	public String toString() {
		return String
				.format("DefaultHead [headLength=%s, packetLength=%s, bodyLength=%s, commandId=%s, sequenceId=%s, headBuffer=%s]",
						headLength, packetLength, bodyLength, String.format("%1$#010x", commandId),
						sequenceId, Hex.encodeHexString(headBuffer));
	}

    
}
