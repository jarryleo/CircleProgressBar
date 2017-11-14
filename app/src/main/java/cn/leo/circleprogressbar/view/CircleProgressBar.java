package cn.leo.circleprogressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Leo on 2017/11/14.
 */

public class CircleProgressBar extends View {

    private Paint mPaint;
    private int mColor;
    private RectF mRectF;
    private int mStrokeWidth;
    private float mStartAngle;
    private float mSweepAngle;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = 15;
        mColor = Color.CYAN;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mStartAngle = -90f;
        mSweepAngle = -90f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height) - mStrokeWidth;
        float left = (width - size) / 2f;
        float top = (height - size) / 2f;
        mRectF = new RectF(left, top, left + size, top + size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);
    }

    /**
     * 进度千分之几，让进度细腻所以采用千分
     *
     * @param permille
     */
    public void progress(int permille) {
        setSweepAngle(permille * 360f / 1000f);
    }

    /**
     * 倒计时
     *
     * @param permille
     */
    public void downProgress(int permille) {
        setSweepAngle(permille * 360f / 1000f);
        setStartAngle(-90 + (1000 - permille) * 360f / 1000f);
    }

    public void setColor(int color) {
        mColor = color;
        postInvalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        postInvalidate();
    }

    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
        postInvalidate();
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
        postInvalidate();
    }
}
