package com.netty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author badBoy <badBoy>
 * Created on 2021-04-28
 * @Description
 */
public class Encoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        writeMessage(out, 1, (MessageLite) msg);
    }

    private void writeMessage(ByteBuf out, int messageType, MessageLite message) throws IOException {
        out.writeInt(messageType);
        //ByteBufOutputStream bbOut = new ByteBufOutputStream(out);
        //message.writeDelimitedTo(bbOut); //此方法会写入一个数据长度和消息内容
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        message.writeTo(byteOutput);
        this.writeBytes(out, byteOutput.toByteArray());
    }

    private void writeMessage(ByteBuf out, int messageType, byte[] bytes) {
        out.writeInt(messageType);

    }

    private void writeBytes(ByteBuf out, byte[] bytes) {
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        this.writeBytes(out, bytes);
    }

}
