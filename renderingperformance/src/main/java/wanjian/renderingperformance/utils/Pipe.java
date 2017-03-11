package wanjian.renderingperformance.utils;

/**
 * Created by wanjian on 2017/3/10.
 */

public abstract class Pipe<T> {
    protected int mSize;

    public Pipe(int size) {
        this.mSize = size;
    }

    public abstract Pipe put(T t);

    public abstract T next();

    public abstract Pipe reset();

    public int size() {
        return mSize;
    }
}
