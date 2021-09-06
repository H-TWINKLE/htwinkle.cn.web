package cn.htwinkle.web.kit;

import okhttp3.*;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 连接池信息
 *
 * @author : twinkle
 * @date : 2021/9/5 23:15
 */
public class OKHttpKit {

    /**
     * OKHttpKit的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(OKHttpKit.class.getName());
    private static final int DEFAULT_TIME_OUT = 10;

    /**
     * 全局实例可以保持http1.1 连接复用，线程池复用， 减少tcp的网络连接，关闭，
     * 如果每次一个请求，在高并发下，thread增多到1W，close_wait持续增加到6k。
     */
    private static final OkHttpClient OK_HTTP_CLIENT = buildHttpClient(DEFAULT_TIME_OUT);

    @NotNull
    private static OkHttpClient buildHttpClient(int timeout) {
        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(50, 5, TimeUnit.MINUTES))
                .connectTimeout(timeout, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).build();
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType FORM_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");


    /**
     * 不同timeout的连接池
     */
    private static final ConcurrentHashMap<Integer, OkHttpClient> cacheClients = new ConcurrentHashMap<>();

    public static OkHttpClient getHttpClient() {
        return OK_HTTP_CLIENT;
    }

    public static OkHttpClient getHttpClient(int timeout) {
        if (timeout == 0 || DEFAULT_TIME_OUT == timeout) {
            return OK_HTTP_CLIENT;
        } else {
            OkHttpClient okHttpClient = cacheClients.get(timeout);
            if (okHttpClient == null) {
                return syncCreateClient(timeout);
            }
            return okHttpClient;
        }
    }

    /**
     * GET请求
     *
     * @param url url
     * @return Optional<String>
     */
    public static String get(String url, int timeout) throws Exception {
        Request request = new Request.Builder().url(url).build();
        ResponseBody body = getHttpClient(timeout).newCall(request).execute().body();
        return null == body ? "" : body.string();

    }

    public static String get(String url) throws Exception {
        return get(url, DEFAULT_TIME_OUT);
    }

    /**
     * POST请求，参数为json格式。
     *
     * @param url         url
     * @param requestBody requestBody
     * @return Optional<String>
     */
    public static String post(String url, RequestBody requestBody, int timeout) throws Exception {
        long start = System.currentTimeMillis();
        try {
            Request request = new Request.Builder().url(url).post(requestBody).build();
            ResponseBody body = getHttpClient(timeout).newCall(request).execute().body();
            return null == body ? "" : body.string();
        } finally {
            loggerRequestTime(url, start);
        }
    }


    public static String post(String url, RequestBody requestBody) throws Exception {
        return post(url, requestBody, DEFAULT_TIME_OUT);
    }

    private static synchronized OkHttpClient syncCreateClient(int timeout) {
        OkHttpClient okHttpClient = cacheClients.get(timeout);
        if (okHttpClient != null) {
            return okHttpClient;
        }
        okHttpClient =
                new OkHttpClient.Builder()
                        .connectTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS)
                        .writeTimeout(timeout, TimeUnit.SECONDS).build();
        cacheClients.put(timeout, okHttpClient);
        return okHttpClient;
    }


    /**
     * 根据不同的类型和requestbody类型来接续参数
     *
     * @param url         url
     * @param mediaType   mediaType
     * @param inputStream inputStream
     * @return String
     * @throws Exception Exception
     */
    public static String post(String url, MediaType mediaType, InputStream inputStream) throws Exception {
        RequestBody body = createRequestBody(mediaType, inputStream);
        Request request = new Request.Builder().url(url).post(body).build();
        return OK_HTTP_CLIENT.newCall(request).execute().body().string();
    }

    /**
     * 记录请求时间
     *
     * @param url   url
     * @param start start
     */
    private static void loggerRequestTime(String url, long start) {
        LOGGER.info(String.format("request url %s ,total time %s ms",
                url, (System.currentTimeMillis() - start)));
    }

    private static RequestBody createRequestBody(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            // @Nullable
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() throws IOException {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
