package cn.leo.circleprogressbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Leo on 2017/11/15.
 */

public class LeoClock extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int boardColor = Color.BLACK;
    private int hourColor = Color.RED;
    private int minuteColor = Color.CYAN;
    private int secondColor = Color.GREEN;
    private int hour, minute, second;
    private float mStrokeWidth;
    private float mR;
    private float mCx;
    private float mCy;

    public LeoClock(Context context) {
        this(context, null);
    }

    public LeoClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeoClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        paint.setStrokeWidth(mStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(mStrokeWidth * 12);
        paint.setTextAlign(Paint.Align.CENTER);
        run();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mR = Math.min(width, height) / 2 - mStrokeWidth;
        mCx = width / 2;
        mCy = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        Log.e("second", hour + ":" + minute + ":" + second);
        float x, y;
        //绘制表盘
        paint.setColor(boardColor);
        canvas.drawCircle(mCx, mCy, mR, paint);
        canvas.drawCircle(mCx, mCy, mStrokeWidth, paint);
        //绘制刻度
        for (int i = 0; i < 60; i++) {
            float x1, x2, y1, y2;
            x1 = (float) ((mR - (i % 5 == 0 ? mStrokeWidth * 5 : mStrokeWidth * 3)) * Math.cos((i * 6 - 90) * Math.PI / 180) + mCx);
            y1 = (float) ((mR - (i % 5 == 0 ? mStrokeWidth * 5 : mStrokeWidth * 3)) * Math.sin((i * 6 - 90) * Math.PI / 180) + mCy);
            x2 = (float) (mR * Math.cos((i * 6 - 90) * Math.PI / 180) + mCx);
            y2 = (float) (mR * Math.sin((i * 6 - 90) * Math.PI / 180) + mCy);
            canvas.drawLine(x1, y1, x2, y2, paint);
            //绘制刻度数字
            if (i % 5 == 0) {
                String h = String.valueOf(i == 0 ? 12 : i / 5);
                x = (float) ((mR - mStrokeWidth * 15) * Math.cos((i * 6 - 90) * Math.PI / 180) + mCx);
                y = (float) ((mR - mStrokeWidth * 15) * Math.sin((i * 6 - 90) * Math.PI / 180) + mCy + mStrokeWidth * 4);
                canvas.drawText(h, x, y, paint);
            }
        }
        //绘制时针
        paint.setColor(hourColor);
        paint.setStrokeWidth(mStrokeWidth * 3);
        x = (float) ((mR - mStrokeWidth * 25) * Math.cos((hour % 12 * 30 - 90 + minute / 2) * Math.PI / 180) + mCx);
        y = (float) ((mR - mStrokeWidth * 25) * Math.sin((hour % 12 * 30 - 90 + minute / 2) * Math.PI / 180) + mCy);
        canvas.drawLine(mCx, mCy, x, y, paint);
        //绘制分针
        paint.setColor(minuteColor);
        paint.setStrokeWidth(mStrokeWidth * 2);
        x = (float) ((mR - mStrokeWidth * 12) * Math.cos((minute * 6 - 90) * Math.PI / 180) + mCx);
        y = (float) ((mR - mStrokeWidth * 12) * Math.sin((minute * 6 - 90) * Math.PI / 180) + mCy);
        canvas.drawLine(mCx, mCy, x, y, paint);
        //绘制秒针
        paint.setColor(secondColor);
        paint.setStrokeWidth(mStrokeWidth);
        x = (float) ((mR - mStrokeWidth * 5) * Math.cos((second * 6 - 90) * Math.PI / 180) + mCx);
        y = (float) ((mR - mStrokeWidth * 5) * Math.sin((second * 6 - 90) * Math.PI / 180) + mCy);
        canvas.drawLine(mCx, mCy, x, y, paint);
    }

    private void run() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                postInvalidate();
            }

            @Override
            public void onFinish() {
                this.start();
            }
        }.start();

    }

}
