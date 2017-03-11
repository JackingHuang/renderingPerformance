package wanjian.renderingperformance.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by wanjian on 2017/3/11.
 */

public class PolylineChartPagePerformance extends DefaultPagePerformanceGraph {
    public PolylineChartPagePerformance(Context context) {
        super(context);
        mPath = new Path();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mWidth);
        mPaint = customerPaint(mPaint);
    }

    protected Paint customerPaint(Paint paint) {
        return paint;
    }

    private Path mPath;

    @Override
    protected void draw(Canvas canvas, float ms) {
        mPath.reset();
        canvas.translate(0, canvas.getHeight());

        mPipe.put(ms);
        mPipe.reset();
        int x = 0;
        Float t;
        while ((t = mPipe.next()) != null) {
            float height = (t * mFactor);
            mPath.lineTo(x, -height);
            x += mTransX;
        }
        canvas.drawPath(mPath, mPaint);
    }
}
