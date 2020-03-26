package com.http.study;

import org.apache.http.HttpEntity;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

/**
 * @author xuedui.zhao
 * @create 2018-10-17
 */
public final class ResponseException extends IOException {

    private Response response;

    public ResponseException(Response response) throws IOException {
        super(buildMessage(response));
        this.response = response;
    }

    /**
     * Wrap a {@linkplain org.elasticsearch.client.ResponseException} with another one with the current
     * stack trace. This is used during synchronous calls so that the caller
     * ends up in the stack trace of the exception thrown.
     */
    ResponseException(ResponseException e) throws IOException {
        super(e.getMessage(), e);
        this.response = e.getResponse();
    }

    private static String buildMessage(Response response) throws IOException {
        String message = String.format(Locale.ROOT,
                "method [%s], host [%s], URI [%s], status line [%s]",
                response.getRequestLine().getMethod(),
                response.getHost(),
                response.getRequestLine().getUri(),
                response.getStatusLine().toString()
        );

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            if (entity.isRepeatable() == false) {
                entity = new BufferedHttpEntity(entity);
                response.getHttpResponse().setEntity(entity);
            }
            message += "\n" + EntityUtils.toString(entity);
        }
        return message;
    }

    /**
     * Returns the {@link org.elasticsearch.client.Response} that caused this exception to be thrown.
     */
    public Response getResponse() {
        return response;
    }
}
