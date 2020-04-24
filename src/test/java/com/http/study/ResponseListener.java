package com.http.study;


/**
 * @author xuedui.zhao
 * @create 2018-10-17
 */
public interface ResponseListener {

    /**
     * Method invoked if the request yielded a successful response
     */
    void onSuccess(Response response);

    /**
     * Method invoked if the request failed. There are two study.main categories of failures: connection failures (usually
     * {@link java.io.IOException}s, or responses that were treated as errors based on their error response code
     * ({@link ResponseException}s).
     */
    void onFailure(Exception exception);
}
