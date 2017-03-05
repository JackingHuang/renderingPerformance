package wanjian.renderingperformance.utils;

import wanjian.renderingperformance.exception.PerformanceException;

/**
 * Created by wanjian on 2017/3/5.
 */

public final class Check {

    public static void isNull(Object o, String msg) {
        if (o == null) {
            throw new PerformanceException(msg);
        }
    }
}
