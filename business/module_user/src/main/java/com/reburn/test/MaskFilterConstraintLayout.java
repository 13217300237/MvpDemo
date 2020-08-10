package com.reburn.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.reburn.user.R;

public class MaskFilterConstraintLayout extends ConstraintLayout {

    private static final int CIRCLE = 1;
    private static final int RECTANGLE = 2;


    private Paint lightPaint;
    private int centerX, centerY;
    private int left, top, right, bottom;
    // 圆角半径
    private float mCornerRadius;
    // 绘制形状
    private int mShapeType;
    /**
     * 发光范围
     */
    private float mRadioRadius;

    public MaskFilterConstraintLayout(Context context) {
        this(context, null);
    }

    public MaskFilterConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskFilterConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int color = 0xEC3E3E;

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MaskFilterConstraintLayout);
            color = ta.getColor(R.styleable.MaskFilterConstraintLayout_mfColor, color);
            mCornerRadius = ta.getDimension(R.styleable.MaskFilterConstraintLayout_mfCornerRadius, 2f);
            mRadioRadius = ta.getDimension(R.styleable.MaskFilterConstraintLayout_mfRadioRadius, 10f);
            mShapeType = ta.getInteger(R.styleable.MaskFilterConstraintLayout_mfShapeType, 2);
            ta.recycle();
        }

        lightPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        lightPaint.setColor(color);
        lightPaint.setMaskFilter(new BlurMaskFilter(mRadioRadius, BlurMaskFilter.Blur.OUTER));
        setFocusable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mShapeType == CIRCLE) {
            mCornerRadius = getMeasuredWidth() / 2;
        }
        centerX = getLeft() + getMeasuredWidth() / 2;
        centerY = getTop() + getMeasuredHeight() / 2;
        left = getLeft();
        top = getTop();
        right = getLeft() + getMeasuredWidth();
        bottom = getTop() + getMeasuredHeight();
        Log.i("HKK", "left:" + left);
        Log.i("HKK", "top:" + top);
        Log.i("HKK", "right:" + right);
        Log.i("HKK", "bottom:" + bottom);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("HKK", "hasFocus:" + hasFocus());
        if (hasFocus() || showFocusForced) {
            if (mShapeType == CIRCLE) {
                canvas.drawCircle(centerX, centerY, mCornerRadius, lightPaint);
            } else {
                RectF rectF = new RectF(left, top, right, bottom);
                canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, lightPaint);
            }
        }

    }

    public void setBlurType(int blurType) {
        switch (blurType) {
            case 0:
                lightPaint.setMaskFilter(new BlurMaskFilter(mRadioRadius, BlurMaskFilter.Blur.INNER));
                break;
            case 1:
                lightPaint.setMaskFilter(new BlurMaskFilter(mRadioRadius, BlurMaskFilter.Blur.NORMAL));
                break;
            case 2:
                lightPaint.setMaskFilter(new BlurMaskFilter(mRadioRadius, BlurMaskFilter.Blur.SOLID));
                break;
            case 3:
                lightPaint.setMaskFilter(new BlurMaskFilter(mRadioRadius, BlurMaskFilter.Blur.OUTER));
                break;
        }

        invalidate();
    }

    private boolean showFocusForced = false;

    public void forceShowFocus() {
        showFocusForced = true;
        invalidate();
    }

    public void hideFocus() {
        showFocusForced = false;
        invalidate();
    }
}
