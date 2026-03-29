package com.web.flowershopping.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderCreateGuard {
    private static final Map<Integer, Long> userOrderTimeMap = new ConcurrentHashMap<>();
    /**
     * true = 允许创建
     * false = 10秒内禁止重复创建
     */
    public static boolean canCreateOrder(Integer userId) {
        long now = System.currentTimeMillis();

        Long result = userOrderTimeMap.compute(userId, (k, oldTime) -> {
            if (oldTime == null || (now - oldTime) >= 10_000) {
                return now;
            }
            return oldTime;
        });

        return result == now;
    }

    /**
     * 下单失败时回滚时间限制
     */
    public static void removeLock(Integer userId) {
        userOrderTimeMap.remove(userId);
    }
}
