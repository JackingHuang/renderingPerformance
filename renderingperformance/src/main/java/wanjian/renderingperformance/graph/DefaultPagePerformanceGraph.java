package wanjian.renderingperformance.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


import wanjian.renderingperformance.utils.ArrayPipe;
import wanjian.renderingperformance.utils.Pipe;

/**
 * Created by wanjian on 2017/3/10.
 */

public class DefaultPagePerformanceGraph extends IPagePerformanceGraph {

    protected Context mContext;

    protected int mGap;

    protected int mWidth;

    protected int mTransX;
    protected Pipe<Float> mPipe;

    protected int mFactor;

    protected Paint mPaint;

    public DefaultPagePerformanceGraph(Context context) {
        this.mContext = context;
        mFactor = getFactor();
        mGap = getGap();
        mWidth = getGraphWidth();
        mTransX = mGap + mWidth;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getGraphColor());

    }

    protected int getFactor() {
        return dp2px(2);
    }

    protected int getGraphColor() {
        return 0x660000FF;
    }

    protected int getGraphWidth() {
        return dp2px(5);
    }

    protected int getGap() {
        return dp2px(2);
    }


    @Override
    public void time(Canvas canvas, float ms) {
        initPipeIfNeeded(canvas);
        draw(canvas,ms);
    }

    protected void draw(Canvas canvas, float ms) {

        canvas.translate(-mTransX, canvas.getHeight());

        mPipe.put(ms);
        mPipe.reset();
        Float t;
        while ((t = mPipe.next()) != null) {
            canvas.translate(mTransX, 0);
            float height = (t * mFactor);
            canvas.drawRect(0, -height, mWidth, 0, mPaint);
        }
    }

    private void initPipeIfNeeded(Canvas canvas) {
        if (mPipe != null) {
            return;
        }
        int canvasWidth = canvas.getWidth();
        mPipe = new ArrayPipe<>(canvasWidth / (mGap + mWidth));
    }


    private int dp2px(float dip) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }


}
