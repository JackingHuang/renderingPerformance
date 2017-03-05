package com.wanjian.performance;

import android.app.Application;

import wanjian.renderingperformance.RenderingPerformance;

/**
 * Created by wanjian on 2017/3/5.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RenderingPerformance.init(this, null);
    }
}
