package com.yiwugou.zipkin4j.client.util;

import java.util.Random;

/**
 * 
 * <pre>
 * GenerateKey
 * </pre>
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2018年1月25日 下午4:18:37
 */
public class GenerateKey {
    private static Random RANDOM = new Random();

    public static Long longKey() {
        return RANDOM.nextLong();
    }
}
