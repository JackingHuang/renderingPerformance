package wanjian.renderingperformance.config;

import wanjian.renderingperformance.filter.ViewFilter;

/**
 * Created by wanjian on 2017/3/5.
 */

public interface Config {
    /**
     * @return view过滤器，决定要统计哪个view的渲染时间
     */
    ViewFilter viewFilter();

    /**
     * @return 每隔多少毫秒统计一次渲染时间
     */
    long timeInterval();

    /**
     * @return 是否在每个view上显示渲染时间
     */
    boolean showTime();

    /**
     * @return 颜色阈值，结合 {@link Config#coverColor()} 来给不同渲染时间的view覆盖不同透明度的颜色
     */
    int threshold();

    /**
     * @return 颜色值
     */
    int coverColor();
}
