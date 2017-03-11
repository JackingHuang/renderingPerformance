## No Red

> No Red 可以在Android手机屏幕上显示view渲染时间，渲染时间越长，view越红，
同时还可以统计页面渲染时间，并可以已柱状图的形式直观的显示当前页面的渲染时间


### 使用方式

* `compile 'com.wanjian:nored:0.0.1'`（审核中2017-3-11）
或者
`compile 'com.wanjian:rp:0.0.1.3'`（已废弃）

* 然后在Application的onCreate中初始化` RenderingPerformance.init(this, null);`,
4.0以下还需要在每个Activity的onResume中调用`RenderingPerformance.resume(Activity activity)`
并在Activity的onPause中调用`RenderingPerformance.pause(Activity activity)`

* 初始化后会在屏幕左上角看到一个性能LOGO，点击即可开关

### 效果图

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/no_red.jpg)

### 原理分析

* 获取当前显示的Activity

```java

    application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

```


* 获取当前Activity的根布局

```java

activity.getWindow().getDecorView()
```

* 遍历布局树，调用每个view 的draw(new Canvas（bmp))，统计该方法的执行时间即可。

    * draw中包含了绘制背景时间+onDraw时间+滚动条（如果有的话）等的时间，比单纯的在onDraw中统计时间更合理些。
view的绘制是从根view的draw方法开始的，该方法内部除了绘制自己还调用了子view的draw方法，如此反复，直到整个view树绘制完成。
由于是手动让view绘制到了bitmap上，所以可能会和实际绘制时间有误差，但可以当成一个相对值，若该值比其他的大很多，就该考虑是不是该view绘制中执行了耗时操作。
另外开发者选项中提供的过度绘制功能有时候并不能反映实际绘制时间，比如我们需要一个圆角的Imageview，我们会在view内部对bitmap进行复杂的处理，等把bitmap处理成圆角后再一次性绘制到canvas上，这样虽然只绘制了一次，但处理bitmap花费的时间可能是绘制的好多倍。

    * 为了保证统计时不卡顿，没有统计 viewgroup的渲染时间，viewgroup的渲染时间包含了所有子view的渲染时间，本身只作为一个容器使用，自身渲染时间一般很短，可以忽略


### 自定义

可以构造一个Config作为参数初始化`RenderingPerformance`,可以指定统计周期，view过滤器，颜色，统计图等，
nored提供了柱状统计图`DefaultPagePerformanceGraph`和折线统计图`PolylineChartPagePerformance`,具体参考Config中Build每个方法注释

```java

    Config config = new Config.Build(this)
//                .timeInterval(100)
                .pagePerformanceGraph(new DefaultPagePerformanceGraph(this) {
                    @Override
                    protected int getFactor() {
                        return super.getFactor() * 20;
                    }

                    @Override
                    protected int getGraphColor() {
                        return 0x660000ff;
                    }
                })
                .build();

        RenderingPerformance.init(this, config);

```

### GitHub

[https://github.com/android-notes/renderingPerformance](https://github.com/android-notes/renderingPerformance)


效果视频

[http://weibo.com/tv/v/EzkwjkjgM?fid=1034:41e23e772b51b3d00eacc1e7b2491975](http://weibo.com/tv/v/EzkwjkjgM?fid=1034:41e23e772b51b3d00eacc1e7b2491975)