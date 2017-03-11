package wanjian.renderingperformance.utils;

import java.util.Arrays;

/**
 * Created by wanjian on 2017/3/10.
 */

public class ArrayPipe<T> extends Pipe<T> {

    private Object[] mDatas;

    private int mPutPointer = -1;
    private int mGetPointer = -1;


    public ArrayPipe(int size) {
        super(size);
        if (size <= 0) {
            throw new RuntimeException("size cannot <= 0 !");
        }
        mDatas = new Object[size];
    }

    @Override
    public Pipe put(T t) {
        if (t == null) {
            throw new RuntimeException("value cannot be null !");
        }
        mPutPointer++;
        if (mPutPointer == mSize) {
            mPutPointer--;

            if (mGetPointer > -1) {
                mGetPointer--;
            }
            System.arraycopy(mDatas, 1, mDatas, 0, mSize - 1);
            mDatas[mSize - 1] = t;
        } else {
            mDatas[mPutPointer] = t;
        }
        return this;
    }


    @Override
    public T next() {

        int position = mGetPointer + 1;
        if (position == mSize || mDatas[position] == null) {
            return null;
        }
        mGetPointer = position;

        return (T) mDatas[position];
    }

    @Override
    public Pipe reset() {
        mGetPointer = -1;
        return this;
    }
}
