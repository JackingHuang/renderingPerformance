package wanjian.renderingperformance.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wanjian on 2017/3/5.
 */

public class DrawingBoardView extends View {
    public DrawingBoardView(Context context) {
        super(context);
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Bitmap mBitmap;

    public void drawPerformance(Bitmap bitmap) {
        mBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
