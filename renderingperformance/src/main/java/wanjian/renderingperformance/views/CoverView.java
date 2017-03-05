package wanjian.renderingperformance.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import wanjian.renderingperformance.R;

/**
 * Created by wanjian on 2017/3/5.
 */

public class CoverView extends RelativeLayout {
    public CoverView(Context context) {
        super(context);
        init(context);
    }


    public CoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.rendering_performance_layout, this);
    }

}
