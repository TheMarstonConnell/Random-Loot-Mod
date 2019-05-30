package xyz.marstonconnell.randomloot.util;

import java.nio.ByteBuffer;

import org.lwjgl.system.windows.MSG;

import io.netty.buffer.ByteBuf;

public class RLMessage extends MSG {

	public RLMessage(ByteBuffer container) {
		super(container);
	}

	int toSend;

	public RLMessage(int toSend) {
		super(getBytes(toSend));

		this.toSend = toSend;
	}

	static ByteBuffer getBytes(Integer i) {

		return ByteBuffer.wrap(new byte[] { i.byteValue() });

	}

}
