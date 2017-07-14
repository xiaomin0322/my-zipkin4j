package com.yiwugou.zipkin4j.client.collector;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.yiwugou.zipkin4j.client.DefaultSpanMetrics;
import com.yiwugou.zipkin4j.client.SpanMetrics;

import lombok.Getter;

public class HttpSpanCollector extends AbstractSpanCollector {
    @Getter
    private String zipkinHost;
    private String url;

    public HttpSpanCollector(String zipkinHost) {
        this(zipkinHost, new DefaultSpanMetrics());
    }

    public HttpSpanCollector(String zipkinHost, SpanMetrics metrics) {
        super(metrics);
        this.url = zipkinHost + (zipkinHost.endsWith("/") ? "" : "/") + "api/v1/spans";
    }

    @Override
    public void sendSpans(byte[] json) throws IOException {
        // intentionally not closing the connection, so as to use keep-alives
        HttpURLConnection connection = (HttpURLConnection) new URL(this.url).openConnection();
        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(60 * 1000);
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setFixedLengthStreamingMode(json.length);
        connection.getOutputStream().write(json);

        try (InputStream in = connection.getInputStream()) {
            while (in.read() != -1) {
                ; // skip
            }
        } catch (IOException e) {
            try (InputStream err = connection.getErrorStream()) {
                if (err != null) { // possible, if the connection was dropped
                    while (err.read() != -1) {
                        ; // skip
                    }
                }
            }
            throw e;
        }
    }
}
