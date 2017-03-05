package wanjian.renderingperformance.config;

import wanjian.renderingperformance.filter.DefaultViewFilter;
import wanjian.renderingperformance.filter.ViewFilter;

/**
 * Created by wanjian on 2017/3/5.
 */

public class DefaultConfig implements Config {
    @Override
    public ViewFilter viewFilter() {
        return new DefaultViewFilter();
    }

    @Override
    public long timeInterval() {
        return 1000;
    }

    @Override
    public boolean showTime() {
        return true;
    }

    @Override
    public int threshold() {
        return 2;
    }

    @Override
    public int coverColor() {
        return 0xFFFF0000;
    }
}
