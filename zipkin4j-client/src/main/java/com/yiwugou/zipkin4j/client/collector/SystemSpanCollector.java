package com.yiwugou.zipkin4j.client.collector;

import java.io.IOException;
import java.util.List;

import zipkin.Span;

/**
 * 
 * <pre>
 * SystemSpanCollector
 * </pre>
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2018年1月25日 下午4:18:30
 */
public class SystemSpanCollector extends AbstractSpanCollector {

    @Override
    public void sendSpans(byte[] json) throws IOException {
        List<Span> spans = super.bytesToList(json);
        for (Span span : spans) {
            System.err.println(span);
        }
    }

}
