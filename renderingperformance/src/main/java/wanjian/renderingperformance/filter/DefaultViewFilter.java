package wanjian.renderingperformance.filter;

import android.view.View;

/**
 * Created by wanjian on 2017/3/5.
 */

public class DefaultViewFilter implements ViewFilter {
    @Override
    public boolean apply(View view) {
        return true;
    }
}
