package cn.leo.circleprogressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Leo on 2017/11/14.
 */

public class NodeProgressBar extends View {

    private String[] mNodes;
    private Paint mBackPaint;
    private Paint mForePaint;
    private TextPaint mTextPaint;
    private int mWidth;
    private int mHeight;
    private float mStrokeWidth;
    private int mProgress;
    private float mPart;

    public NodeProgressBar(Context context) {
        this(context, null);
    }

    public NodeProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        //背景
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint.setColor(Color.GRAY);
        mBackPaint.setStrokeWidth(mStrokeWidth);
        //前景
        mForePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForePaint.setColor(Color.GREEN);
        mForePaint.setStrokeWidth(mStrokeWidth * 2);
        //文字
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mStrokeWidth * 15);
        mTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (mNodes != null) {
            setNodes(mNodes);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, mHeight / 5, mWidth, mHeight / 5, mBackPaint);
        float progress = mProgress * mPart - (mPart / 2);
        canvas.drawLine(0, mHeight / 5, progress, mHeight / 5, mForePaint);
        for (int i = 0; i < mNodes.length; i++) {
            String node = mNodes[i];
            float x = (i + 1) * mPart - (mPart / 2);
            if (x <= progress) {
                mForePaint.setStyle(Paint.Style.STROKE);
                mForePaint.setStrokeWidth(mStrokeWidth / 2);
                canvas.drawCircle(x, mHeight / 5, mStrokeWidth * 6, mForePaint);
                mForePaint.setStyle(Paint.Style.FILL);
                mForePaint.setStrokeWidth(mStrokeWidth * 2);
                canvas.drawCircle(x, mHeight / 5, mStrokeWidth * 4, mForePaint);
            } else {
                canvas.drawCircle(x, mHeight / 5, mStrokeWidth * 4, mBackPaint);
            }
            canvas.drawText(node, x, mHeight * 3 / 5, mTextPaint);
        }

    }

    /**
     * 传入节点字符串
     *
     * @param nodes
     */
    public void setNodes(String[] nodes) {
        if (nodes == null || nodes.length == 0)
            throw new IllegalArgumentException("nodes is empty");
        mNodes = nodes;
        //分段长度
        mPart = mWidth / (mNodes.length);
        postInvalidate();
    }

    /**
     * 完成度，进度高亮到节点
     *
     * @param progress 0-mNodes.length
     */
    public void setProgress(int progress) {
        if (mNodes == null || mNodes.length == 0)
            throw new IllegalArgumentException("nodes is empty");
        mProgress = progress;
    }

    public void setBackColor(int backColor) {
        mBackPaint.setColor(backColor);
        postInvalidate();
    }

    public void setForeColor(int foreColor) {
        mForePaint.setColor(foreColor);
        postInvalidate();
    }

    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
        postInvalidate();
    }
}
