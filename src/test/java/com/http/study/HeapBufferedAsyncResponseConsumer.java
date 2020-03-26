package com.http.study;

import org.apache.http.ContentTooLongException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.ContentDecoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.entity.ContentBufferEntity;
import org.apache.http.nio.protocol.AbstractAsyncResponseConsumer;
import org.apache.http.nio.util.ByteBufferAllocator;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.nio.util.SimpleInputBuffer;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author xuedui.zhao
 * @create 2018-10-17
 */
public class HeapBufferedAsyncResponseConsumer extends AbstractAsyncResponseConsumer<HttpResponse> {

    private final int bufferLimitBytes;
    private volatile HttpResponse response;
    private volatile SimpleInputBuffer buf;

    /**
     * Creates a new instance of this consumer with the provided buffer limit
     */
    public HeapBufferedAsyncResponseConsumer(int bufferLimit) {
        if (bufferLimit <= 0) {
            throw new IllegalArgumentException("bufferLimit must be greater than 0");
        }
        this.bufferLimitBytes = bufferLimit;
    }

    /**
     * Get the limit of the buffer.
     */
    public int getBufferLimit() {
        return bufferLimitBytes;
    }

    @Override
    protected void onResponseReceived(HttpResponse response) throws HttpException, IOException {
        this.response = response;
    }

    @Override
    protected void onEntityEnclosed(HttpEntity entity, ContentType contentType) throws IOException {
        long len = entity.getContentLength();
        if (len > bufferLimitBytes) {
            throw new ContentTooLongException("entity content is too long [" + len +
                    "] for the configured buffer limit [" + bufferLimitBytes + "]");
        }
        if (len < 0) {
            len = 4096;
        }
        this.buf = new SimpleInputBuffer((int) len, getByteBufferAllocator());
        this.response.setEntity(new ContentBufferEntity(entity, this.buf));
    }

    /**
     * Returns the instance of {@link ByteBufferAllocator} to use for content buffering.
     * Allows to plug in any {@link ByteBufferAllocator} implementation.
     */
    protected ByteBufferAllocator getByteBufferAllocator() {
        return HeapByteBufferAllocator.INSTANCE;
    }

    @Override
    protected void onContentReceived(ContentDecoder decoder, IOControl ioctrl) throws IOException {
        this.buf.consumeContent(decoder);
    }

    @Override
    protected HttpResponse buildResult(HttpContext context) throws Exception {
        return response;
    }

    @Override
    protected void releaseResources() {
        response = null;
    }

}
