package wanjian.renderingperformance.graph;

import android.graphics.Canvas;

/**
 * Created by wanjian on 2017/3/10.
 */

public abstract class IPagePerformanceGraph {

    public abstract void time(Canvas canvas, float ms);
}
