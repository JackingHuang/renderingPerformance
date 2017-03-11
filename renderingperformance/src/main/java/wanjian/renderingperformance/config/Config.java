package wanjian.renderingperformance.config;

import android.content.Context;

import wanjian.renderingperformance.graph.DefaultPagePerformanceGraph;
import wanjian.renderingperformance.graph.IPagePerformanceGraph;
import wanjian.renderingperformance.exception.PerformanceException;
import wanjian.renderingperformance.filter.DefaultViewFilter;
import wanjian.renderingperformance.filter.ViewFilter;
import wanjian.renderingperformance.utils.Check;

/**
 * Created by wanjian on 2017/3/5.
 */

public class Config {

    private ViewFilter mViewFilter;
    private long mTimeInterval;
    private boolean mShowTime;
    private int mThreshold;
    private int mCoverColor;
    private IPagePerformanceGraph mGraph;

    private Config(Build build) {
        mViewFilter = build.mViewFilter;
        mTimeInterval = build.mTimeInterval;
        mShowTime = build.mShowTime;
        mThreshold = build.mThreshold;
        mCoverColor = build.mCoverColor;
        mGraph = build.mGraph;
    }


    public ViewFilter viewFilter() {
        return mViewFilter;
    }


    public long timeInterval() {
        return mTimeInterval;
    }


    public boolean showTime() {
        return mShowTime;
    }


    public int threshold() {
        return mThreshold;
    }


    public int coverColor() {
        return mCoverColor;
    }


    public IPagePerformanceGraph pagePerformanceGraph() {
        return mGraph;
    }


    public static class Build {
        ViewFilter mViewFilter = new DefaultViewFilter();
        long mTimeInterval = 100;
        boolean mShowTime = true;
        int mThreshold = 2;
        int mCoverColor = 0xFFFF0000;
        IPagePerformanceGraph mGraph;

        Context mContext;

        public Build(Context context) {
            mContext = context;
            mGraph = new DefaultPagePerformanceGraph(context);
        }

        /**
         *  view过滤器，决定要统计哪个view的渲染时间
         */
        public Build viewFilter(ViewFilter viewFilter) {
            Check.isNull(viewFilter, "viewFilter cannot be null !");
            mViewFilter = viewFilter;
            return this;
        }

        /**
         *  每隔多少毫秒统计一次渲染时间
         */
        public Build timeInterval(long time) {
            if (time <= 16) {
                throw new PerformanceException("timeInterval cannot <= 16 !");
            }
            mTimeInterval = time;
            return this;
        }

        /**
         *  是否在每个view上显示渲染时间
         */
        public Build showTime(boolean show) {
            mShowTime = show;
            return this;
        }

        /**
         * @return 颜色阈值，结合 {@link Config#coverColor()} 来给不同渲染时间的view覆盖不同透明度的颜色
         */
        public Build threshold(int value) {
            if (value <= 0) {
                throw new PerformanceException("threshold cannot <= 0 !");
            }
            mThreshold = value;
            return this;
        }

        /**
         *  颜色值
         */
        public Build coverColor(int color) {
            mCoverColor = color;
            return this;
        }

        /**
         * 页面渲染性能统计图
         */
        public Build pagePerformanceGraph(IPagePerformanceGraph graph) {
            Check.isNull(graph, "graph cannot be null !");
            mGraph = graph;
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }

}
