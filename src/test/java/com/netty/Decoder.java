package com.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author badBoy <badBoy>
 * Created on 2021-04-28
 * @Description 反序列化 parseDelimitedFrom
 */
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //预读8个字节，
        int availableBytes = in.readableBytes();
        if (availableBytes < 8) {
            return;
        }
        //记录当前的起始位置
        in.markReaderIndex();
        int messageType = in.readInt();
        int payloadLength = in.readInt();
        //消息尚未完全可读，半包状态
        if (in.readableBytes() < payloadLength) {
            //回到起始位置
            in.resetReaderIndex();
            return;
        }
        // 消息可读
        byte[] payload = new byte[payloadLength];
        in.readBytes(payload);
        out.add(new String(payload));
    }
}
