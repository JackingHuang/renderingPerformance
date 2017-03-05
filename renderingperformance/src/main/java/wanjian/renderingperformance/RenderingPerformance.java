package wanjian.renderingperformance;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import wanjian.renderingperformance.adapter.LifecycleCallbacksAdapter;
import wanjian.renderingperformance.config.Config;
import wanjian.renderingperformance.config.DefaultConfig;
import wanjian.renderingperformance.utils.Check;

/**
 * Created by wanjian on 2017/3/5.
 * <p>
 * 使用前必须调用 {@link RenderingPerformance#init(Application, Config)} 初始化，android API 14 及以上只需要调用init初始化即可，
 * API 14 以下还需要在每个Activity的onResume中调用  {@link RenderingPerformance#resume(Activity)}
 * 并且在每个Activity的onPause中调用 {@link RenderingPerformance#pause(Activity)}
 */

public final class RenderingPerformance {

    private static Manager sManager;


    public static void init(Application application, Config config) {
        Check.isNull(application, "application should not be null !");

        if (config == null) {
            config = new DefaultConfig();
        }
        sManager = new Manager(application, config);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(new LifecycleCallbacksAdapter() {
                @Override
                public void onActivityPaused(Activity activity) {
                    super.onActivityPaused(activity);
                    sManager.detach(activity);
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    super.onActivityResumed(activity);
                    sManager.attach(activity);
                }
            });
        }
    }


    public static void resume(Activity activity) {
        Check.isNull(sManager, "init first !");
        Check.isNull(activity, "activity should not be null !");

        sManager.attach(activity);
    }


    public static void pause(Activity activity) {
        Check.isNull(sManager, "init first !");
        Check.isNull(activity, "activity should not be null !");

        sManager.detach(activity);
    }


}
