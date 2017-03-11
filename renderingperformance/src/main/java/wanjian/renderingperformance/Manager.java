package wanjian.renderingperformance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import wanjian.renderingperformance.config.Config;
import wanjian.renderingperformance.views.CoverView;
import wanjian.renderingperformance.views.DrawingBoardView;

/**
 * Created by wanjian on 2017/3/5.
 */

final class Manager {
    private Config mConfig;

    private View mRootView;
    private View mCoverView;
    private Bitmap mDrawingBoard;
    private Canvas mDrawingBoardCanvas;
    private Bitmap mPerformance;
    private Canvas mPerformanceCanvas;
    private Handler mMetronome;
    private DrawingBoardView mDrawingBoardView;
    private Runnable mRhythm;
    private Paint mPaint;
    private int mPageTextSize;
    private int mViewTextSize;

    public Manager(Context context, Config config) {
        this.mConfig = config;
        mCoverView = new CoverView(context);

        int w = context.getResources().getDisplayMetrics().widthPixels;
        int h = context.getResources().getDisplayMetrics().heightPixels;
        mDrawingBoard = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mPerformance = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mMetronome = new Handler(Looper.getMainLooper());
        mRhythm = new Rhythm();

        mDrawingBoardCanvas = new Canvas(mDrawingBoard);
        mPerformanceCanvas = new Canvas(mPerformance);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPageTextSize = (int) context.getResources().getDimension(R.dimen.rp_page_performance_text_size);
        mViewTextSize = (int) context.getResources().getDimension(R.dimen.rp_text_size);
        init();
    }

    private void init() {
        mDrawingBoardView = (DrawingBoardView) mCoverView.findViewById(R.id.drawingBoard);
        View btn = mCoverView.findViewById(R.id.on_off);
        btn.setSelected(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mMetronome.post(mRhythm);
                } else {
                    mMetronome.removeCallbacks(mRhythm);
                    mPerformance.eraseColor(0);
                    mDrawingBoardView.drawPerformance(mPerformance);
                }
            }
        });
    }

    private class Rhythm implements Runnable {

        @Override
        public void run() {
            if (mRootView == null) {
                mMetronome.postDelayed(this, mConfig.timeInterval());
                return;
            }
            analysePerformance();
            mMetronome.postDelayed(this, mConfig.timeInterval());
        }
    }

    private void analysePerformance() {

        mPerformance.eraseColor(0);

        traversalLayout(mRootView);
        analysePagePerformance();

        mDrawingBoardView.drawPerformance(mPerformance);
    }


    private void traversalLayout(View view) {
        if (view instanceof CoverView) {
            return;
        }
        if (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) {
            return;
        }

        if (!mConfig.viewFilter().apply(view)) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                traversalLayout(viewGroup.getChildAt(i));
            }
        } else {
            analyseViewPerformance(view);
        }

    }

    private void analyseViewPerformance(View view) {
        mDrawingBoardCanvas.save();
        long start = System.nanoTime();
        view.draw(mDrawingBoardCanvas);
        long duration = System.nanoTime() - start;
        mDrawingBoardCanvas.restore();
        float time = duration / 10_000 / 100f;

        int configColor = mConfig.coverColor() & 0x00FFFFFF;
        int alpha = (int) (time / mConfig.threshold() * 0x0000000A);
        if (alpha > 0x000000FF) {
            alpha = 0x000000FF;
        }
        int coverColor = alpha << 24 | configColor;

        mPaint.setColor(coverColor);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPerformanceCanvas.drawRect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight(), mPaint);
        if (mConfig.showTime()) {
            String msg = String.valueOf(time);
            mPaint.setTextSize(mViewTextSize);
            mPaint.setColor(0x88FFFFFF);
            mPerformanceCanvas.drawRect(location[0], location[1], location[0] + mPaint.measureText(msg), location[1] + mViewTextSize, mPaint);
            mPaint.setColor(0xFF000000);
            mPerformanceCanvas.drawText(msg, location[0], location[1] + mViewTextSize, mPaint);
        }
    }

    private void analysePagePerformance() {


        long duration = 0;
//        ViewGroup viewGroup = ((ViewGroup) mRootView);
//        for (int i = viewGroup.getChildCount() - 1; i > -1; i--) {
//            View child = viewGroup.getChildAt(i);
//            if (child instanceof CoverView) {
//                continue;
//            }
//            mDrawingBoardCanvas.save();
//            long start = System.nanoTime();
//
//            child.draw(mDrawingBoardCanvas);
//
//            duration += (System.nanoTime() - start);
//            mDrawingBoardCanvas.restore();
//        }
        View content = mRootView.findViewById(android.R.id.content);
        if (content == null) {
            return;
        }
        mDrawingBoardCanvas.save();
        long start = System.nanoTime();

        content.draw(mDrawingBoardCanvas);

        duration += (System.nanoTime() - start);
        mDrawingBoardCanvas.restore();

        float t = duration / 10_000 / 100f;
        mPerformanceCanvas.save();
        mConfig.pagePerformanceGraph().time(mPerformanceCanvas,  t);
        mPerformanceCanvas.restore();
        String time = String.valueOf(t);

        mPaint.setTextSize(mPageTextSize);
        mPaint.setColor(0xFFFF0000);
        mPerformanceCanvas.drawText(time, 10, mRootView.getHeight() - 10, mPaint);

    }

    public void attach(Activity activity) {
        mRootView = activity.getWindow().getDecorView();
        if (mCoverView.getParent() != null) {
            ((ViewGroup) mCoverView.getParent()).removeView(mCoverView);
        }
        ((ViewGroup) mRootView).addView(mCoverView);
    }

    public void detach(Activity activity) {
        mRootView = null;
        ((ViewGroup) activity.getWindow().getDecorView()).removeView(mCoverView);
    }
}
