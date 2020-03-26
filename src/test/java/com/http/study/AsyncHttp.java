package com.http.study;

import com.google.common.base.Joiner;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.AuthCache;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 异步http
 *
 * @author xuedui.zhao
 * @create 2018-10-16
 */
public class AsyncHttp {

    static final int DEFAULT_BUFFER_LIMIT = 100 * 1024 * 1024;

    @Test
    public void testAsync() {
        CloseableHttpAsyncClient httpClient = null;
        try {
            HttpHost host = new HttpHost(InetAddress.getByName("localhost"), 9200, "http");

            String endPoint = Joiner.on("/").join("megacorp", "_search");

            URI uri = buildUri("",endPoint, Collections.singletonMap("pretty", "true"));

            HttpPost httpPost = new HttpPost(uri);

            String dsl = buildDsl();

            HttpEntity entity = new NStringEntity(dsl, ContentType.APPLICATION_JSON);

            HttpRequestBase request = addRequestBody(httpPost, entity);
            httpClient = AccessController.doPrivileged(new PrivilegedAction<CloseableHttpAsyncClient>() {
                @Override
                public CloseableHttpAsyncClient run() {
                    return createHttpClient();
                }
            });
            httpClient.start();

            final HttpAsyncRequestProducer requestProducer = HttpAsyncMethods.create(host, request);

            final HttpAsyncResponseConsumer<HttpResponse> asyncResponseConsumer =
                    new HeapBufferedAsyncResponseConsumer(DEFAULT_BUFFER_LIMIT);

            final HttpClientContext context = HttpClientContext.create();
            AuthCache authCache = new BasicAuthCache();
            authCache.put(host, new BasicScheme());
            context.setAuthCache(authCache);

            SyncResponseListener responseListener = new SyncResponseListener(10*10000000);

            FailureTrackingResponseListener listener = new FailureTrackingResponseListener(responseListener);

      httpClient.execute(
          requestProducer,
          asyncResponseConsumer,
          context,
          new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
              try {
                System.out.println("httpResponse == " + httpResponse.getEntity());
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Response response = new Response(request.getRequestLine(), host, httpResponse);
                if (isSuccessfulResponse(statusCode)) {
                  // onResponse(host);
                  listener.onSuccess(response);
                } else {
                  ResponseException responseException = new ResponseException(response);
                  if (isRetryStatus(statusCode)) {
                    // mark host dead and retry against next one
                    onFailure(host);
                  } else {
                    // mark host alive and don't retry, as the error should be a request problem
                    // onResponse(host);
                    listener.onDefinitiveFailure(responseException);
                  }
                }
              } catch (Exception e) {
                listener.onDefinitiveFailure(e);
              }
            }

            @Override
            public void failed(Exception failure) {
              try {
                onFailure(host);
              } catch (Exception e) {
                listener.onDefinitiveFailure(e);
              }
            }

            @Override
            public void cancelled() {
              listener.onDefinitiveFailure(new ExecutionException("request was cancelled", null));
            }
          });

        System.out.println("response===" + EntityUtils.toString(responseListener.get().getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String buildDsl() {
    String dsl =
        "{\n"
            + "    \"query\" : {\n"
            + "        \"match\" : {\n"
            + "            \"last_name\" : \"Smith\"\n"
            + "        }\n"
            + "    }\n"
            + "}";
        return dsl;
    }

    private CloseableHttpAsyncClient createHttpClient() {
        //default timeouts are all infinite
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setSocketTimeout(30000);

        try {
            HttpAsyncClientBuilder httpClientBuilder = HttpAsyncClientBuilder.create().setDefaultRequestConfig(requestConfigBuilder.build())
                    //default settings for connection pooling may be too constraining
                    .setMaxConnPerRoute(10).setMaxConnTotal(30)
                    .setSSLContext(SSLContext.getDefault());

            final HttpAsyncClientBuilder finalBuilder = httpClientBuilder;
            return AccessController.doPrivileged(new PrivilegedAction<CloseableHttpAsyncClient>() {
                @Override
                public CloseableHttpAsyncClient run() {
                    return finalBuilder.build();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not create the default ssl context", e);
        }
    }

    private void onFailure(HttpHost host) {
        new FailureListener() {

            @Override
            public void onFailure(HttpHost host) {
                super.onFailure(host);
            }
        }.onFailure(host);
    }

    public static class FailureListener {
        /**
         * Notifies that the host provided as argument has just failed
         */
        public void onFailure(HttpHost host) {

        }
    }

    private static boolean isRetryStatus(int statusCode) {
        switch(statusCode) {
            case 502:
            case 503:
            case 504:
                return true;
        }
        return false;
    }

    private static boolean isSuccessfulResponse(int statusCode) {
        return statusCode < 300;
    }

    private static HttpRequestBase addRequestBody(HttpRequestBase httpRequest, HttpEntity entity) {
        if (entity != null) {
            if (httpRequest instanceof HttpEntityEnclosingRequestBase) {
                ((HttpEntityEnclosingRequestBase)httpRequest).setEntity(entity);
            } else {
                throw new UnsupportedOperationException(httpRequest.getMethod() + " with body is not supported");
            }
        }
        return httpRequest;
    }

    static URI buildUri(String pathPrefix, String path, Map<String, String> params) {
        Objects.requireNonNull(path, "path must not be null");
        try {
            String fullPath;
            if (pathPrefix != null) {
                if (path.startsWith("/")) {
                    fullPath = pathPrefix + path;
                } else {
                    fullPath = pathPrefix + "/" + path;
                }
            } else {
                fullPath = path;
            }

            URIBuilder uriBuilder = new URIBuilder(fullPath);
            for (Map.Entry<String, String> param : params.entrySet()) {
                uriBuilder.addParameter(param.getKey(), param.getValue());
            }
            return uriBuilder.build();
        } catch(URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    static class SyncResponseListener implements ResponseListener {
        private final CountDownLatch latch = new CountDownLatch(1);
        private final AtomicReference<Response> response = new AtomicReference<>();
        private final AtomicReference<Exception> exception = new AtomicReference<>();

        private final long timeout;

        SyncResponseListener(long timeout) {
            assert timeout > 0;
            this.timeout = timeout;
        }

        @Override
        public void onSuccess(Response response) {
            Objects.requireNonNull(response, "response must not be null");
            boolean wasResponseNull = this.response.compareAndSet(null, response);
            if (wasResponseNull == false) {
                throw new IllegalStateException("response is already set");
            }

            latch.countDown();
        }

        @Override
        public void onFailure(Exception exception) {
            Objects.requireNonNull(exception, "exception must not be null");
            boolean wasExceptionNull = this.exception.compareAndSet(null, exception);
            if (wasExceptionNull == false) {
                throw new IllegalStateException("exception is already set");
            }
            latch.countDown();
        }

        /**
         * Waits (up to a timeout) for some result of the request: either a response, or an exception.
         */
        Response get() throws IOException {
            try {
                //providing timeout is just a safety measure to prevent everlasting waits
                //the different client timeouts should already do their jobs
                if (latch.await(timeout, TimeUnit.MILLISECONDS) == false) {
                    throw new IOException("listener timeout after waiting for [" + timeout + "] ms");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("thread waiting for the response was interrupted", e);
            }

            Exception exception = this.exception.get();
            Response response = this.response.get();
            if (exception != null) {
                if (response != null) {
                    IllegalStateException e = new IllegalStateException("response and exception are unexpectedly set at the same time");
                    e.addSuppressed(exception);
                    throw e;
                }
                /*
                 * Wrap and rethrow whatever exception we received, copying the type
                 * where possible so the synchronous API looks as much as possible
                 * like the asynchronous API. We wrap the exception so that the caller's
                 * signature shows up in any exception we throw.
                 */
                if (exception instanceof ResponseException) {
                    throw (ResponseException) exception;
                }
                if (exception instanceof ConnectTimeoutException) {
                    ConnectTimeoutException e = new ConnectTimeoutException(exception.getMessage());
                    e.initCause(exception);
                    throw e;
                }
                if (exception instanceof SocketTimeoutException) {
                    SocketTimeoutException e = new SocketTimeoutException(exception.getMessage());
                    e.initCause(exception);
                    throw e;
                }
                if (exception instanceof ConnectionClosedException) {
                    ConnectionClosedException e = new ConnectionClosedException(exception.getMessage());
                    e.initCause(exception);
                    throw e;
                }
                if (exception instanceof SSLHandshakeException) {
                    SSLHandshakeException e = new SSLHandshakeException(exception.getMessage());
                    e.initCause(exception);
                    throw e;
                }
                if (exception instanceof IOException) {
                    throw new IOException(exception.getMessage(), exception);
                }
                if (exception instanceof RuntimeException){
                    throw new RuntimeException(exception.getMessage(), exception);
                }
                throw new RuntimeException("error while performing request", exception);
            }

            if (response == null) {
                throw new IllegalStateException("response not set and no exception caught either");
            }
            return response;
        }
    }

    static class FailureTrackingResponseListener {
        private final ResponseListener responseListener;
        private volatile Exception exception;

        FailureTrackingResponseListener(ResponseListener responseListener) {
            this.responseListener = responseListener;
        }

        /**
         * Notifies the caller of a response through the wrapped listener
         */
        void onSuccess(Response response) {
            responseListener.onSuccess(response);
        }

        /**
         * Tracks one last definitive failure and returns to the caller by notifying the wrapped listener
         */
        void onDefinitiveFailure(Exception exception) {
            trackFailure(exception);
            responseListener.onFailure(this.exception);
        }

        /**
         * Tracks an exception, which caused a retry hence we should not return yet to the caller
         */
        void trackFailure(Exception exception) {
            this.exception = addSuppressedException(this.exception, exception);
        }
    }

    private static Exception addSuppressedException(Exception suppressedException, Exception currentException) {
        if (suppressedException != null) {
            currentException.addSuppressed(suppressedException);
        }
        return currentException;
    }


//    @Test
//    public void testAsync02() {
//        try {
//            String url = "http://localhost:9200/megacorp/employee/_search";
//            QunarAsyncClient client = new QunarAsyncClient();
//            Future<String> future = client.get(url, new TextHandler(url));
//            System.out.println( future.get());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    static class TextHandler implements AsyncHandler<String> {
//        private static final int DEF_CONTENT_LEN = 500000;
//        private final String url;
//        private volatile Charset charset = Charset.forName("GB18030");;
//        private volatile ByteArrayOutputStream buffer;
//
//        public TextHandler(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public STATE onStatusReceived(HttpResponseStatus status) throws Exception {
//            if (status.getStatusCode() != 200) {
//                return STATE.ABORT;
//            }
//            return STATE.CONTINUE;
//        }
//
//        @Override
//        public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
//            Charset charset = null;
//
//            String stype = headers.getHeaders().getFirstValue("Content-Type");
//
//            if (stype != null) {
//                ContentType contentType = ContentType.parse(stype);
//                if (contentType.getCharset() != null) {
//                    charset = contentType.getCharset();
//                }
//            }
//            if (charset == null)
//                charset = charset;
//
//            this.charset = charset;
//
//            if (headers.getHeaders().getFirstValue("Content-Length") != null) {
//                int contentLength = Integer.valueOf(headers.getHeaders().getFirstValue("Content-Length"));
//                if (contentLength <= 0)
//                    return AsyncHandler.STATE.ABORT;
//                buffer = new ByteArrayOutputStream(contentLength);
//            }
//
//            return STATE.CONTINUE;
//        }
//
//        @Override
//        public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
//            if (buffer == null)
//                buffer = new ByteArrayOutputStream(DEF_CONTENT_LEN);
//            buffer.write(bodyPart.getBodyPartBytes());
//            return STATE.CONTINUE;
//        }
//
//        @Override
//        public void onThrowable(Throwable t) {
//        }
//
//        @Override
//        public String onCompleted() throws Exception {
//            if (buffer == null)
//                return null;
//            return buffer.toString(charset.name());
//        }
//    }
}

