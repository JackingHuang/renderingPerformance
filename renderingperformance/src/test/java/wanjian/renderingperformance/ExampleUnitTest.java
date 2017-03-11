package wanjian.renderingperformance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        int mSavePointer = 4;
        int mSize = 5;

        for (int i = 0; i < 20; i++) {
            mSavePointer = (--mSavePointer % mSize + mSize) % mSize;
            System.out.println(mSavePointer);
        }

    }
}