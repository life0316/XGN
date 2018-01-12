package com.haoxi.xgn.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.haoxi.xgn.R;
import com.haoxi.xgn.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class TenHistroyView extends View {

    private String mTimeText;

    private int mTimeTextColor;

    private int mBgColor;

    private int mTimeTextSize;

    private int total = 1000;
    private List<HistoryBean> eachList = new ArrayList<>();

    private Rect mBound;
    private Paint mPaint;
    // 画实心圆的画笔
    private Paint mCirclePaint;

    // 半径
    private float mRadius;


    public TenHistroyView(Context context) {
        this(context,null);
    }

    public TenHistroyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setEachList(List<HistoryBean> eachList) {
        this.eachList = eachList;
        invalidate();
    }

    public TenHistroyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TenHistoryView);

        if (a != null){
            mTimeText = a.getString(R.styleable.TenHistoryView_titleText);
            mTimeTextColor = a.getColor(R.styleable.TenHistoryView_titleTextColor,Color.RED);
            mBgColor = a.getColor(R.styleable.TenHistoryView_bgColor,Color.GRAY);
            mTimeTextSize = a.getDimensionPixelSize(R.styleable.TenHistoryView_titleTextSize,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            a.recycle();
        }

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTimeTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTimeText, 0, mTimeText.length(), mBound);
        //内圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mTimeTextColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();

        int jgWidth = mWidth * 1/16;
        int jgHeight = 20;
        int earchWidth = jgWidth *1/2;
        int earchHeight = mHeight - 4 *jgHeight;

        mPaint.setColor(mBgColor);
        mCirclePaint.setColor(mBgColor);
        mRadius = jgWidth * 1/4;
        for (int i=0;i<eachList.size();i++){
            canvas.drawRect(jgWidth + (jgWidth + earchWidth) * i, jgHeight, (earchWidth + jgWidth) * (i+1), mHeight - 3 *jgHeight, mPaint);

            int xCenter = (earchWidth + jgWidth) * (i+1) - earchWidth * 1/2;
            int yCenter = jgHeight;

            //内圆
            canvas.drawCircle(xCenter, yCenter, mRadius, mCirclePaint);
        }

        mPaint.setColor(mTimeTextColor);
        mCirclePaint.setColor(mTimeTextColor);
        for (int i=0;i<eachList.size();i++){
            HistoryBean dd = eachList.get(i);
            int fasdfas = dd.getStepNum();
            String time = dd.getTime();
            int height = (int) ((float)fasdfas/total * earchHeight);

            canvas.drawRect(jgWidth + (jgWidth + earchWidth) * i, mHeight - 3 *jgHeight - height, (earchWidth + jgWidth) * (i+1), mHeight - 3 *jgHeight, mPaint);
            canvas.drawText(time, jgWidth + (jgWidth + earchWidth) * i - earchWidth / 2, mHeight - jgHeight - mBound.height() / 2, mPaint);

            int xCenter = (earchWidth + jgWidth) * (i+1) - earchWidth * 1/2;
            int yCenter = mHeight - 3 *jgHeight - height - earchWidth * 1/8;

            //内圆
            canvas.drawCircle(xCenter, yCenter, mRadius, mCirclePaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTimeTextSize);
            mPaint.getTextBounds(mTimeText, 0, mTimeText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTimeTextSize);
            mPaint.getTextBounds(mTimeText, 0, mTimeText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
         setMeasuredDimension(width, height);
    }
}
