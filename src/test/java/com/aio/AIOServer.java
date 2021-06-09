package com.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.locks.LockSupport;

import lombok.SneakyThrows;

/**
 * @author badBoy <badBoy>
 * Created on 2021-06-03
 * @Description
 */
public class AIOServer {

    public static void main(String[] args) throws Exception {
        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9000));
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @SneakyThrows
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                System.out.println("--1 name--" + Thread.currentThread().getName());
                serverChannel.accept(attachment, this);
                System.out.println("remoteAddress: " + socketChannel.getRemoteAddress());
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("--2 name--" + Thread.currentThread().getName());
                        attachment.flip();
                        System.out.println(new String(attachment.array(), 0, result));
                        socketChannel.write(ByteBuffer.wrap("HelloClient".getBytes()));
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        System.out.println("--main--" + Thread.currentThread().getName());

        LockSupport.park();
    }

}
