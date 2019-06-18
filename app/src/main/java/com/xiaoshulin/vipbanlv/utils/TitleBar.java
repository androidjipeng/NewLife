package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;


/**
 * 标题栏
 *
 * @author zhaoweiwei@qk365.com
 */
public class TitleBar extends RelativeLayout {

    public final int TYPE_NONE = 0;
    public final int TYPE_ICON = 1;
    public final int TYPE_TEXT = 2;
    public final int TYPE_BOTH = 3;

    private int mLeftType;
    private int mRightType;

    private String mTextLeft;
    private String mTextRight;
    private String mTextCenter;
    private Drawable mIconLeft;
    private Drawable mIconRight;

    private int mColorLeftText;
    private int mColorCenterText;
    private int mColorRightText;

    private float mSizeLeftText;
    private float mSizeCenterText;
    private float mSizeRightText;

    private int mLeftMinWidth;
    private int mRightMinWidth;

    private View mLeftView;
    private View mCenterView;
    private View mRightView;

    private int minCenterMargin;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

            mLeftType = ta.getInt(R.styleable.TitleBar_leftType, TYPE_NONE);
            mRightType = ta.getInt(R.styleable.TitleBar_rightType, TYPE_NONE);

            mTextLeft = ta.getString(R.styleable.TitleBar_leftText);
            mTextCenter = ta.getString(R.styleable.TitleBar_centerText);
            mTextRight = ta.getString(R.styleable.TitleBar_rightText);

            mIconLeft = ta.getDrawable(R.styleable.TitleBar_leftIcon);
            mIconRight = ta.getDrawable(R.styleable.TitleBar_rightIcon);

            mColorLeftText = ta.getColor(R.styleable.TitleBar_leftTextColor, Color.WHITE);
            mColorCenterText = ta.getColor(R.styleable.TitleBar_centerTextColor, Color.WHITE);
            mColorRightText = ta.getColor(R.styleable.TitleBar_rightTextColor, Color.WHITE);

            int defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics());

            mSizeLeftText = ta.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, defaultTextSize);
            mSizeCenterText = ta.getDimensionPixelSize(R.styleable.TitleBar_centerTextSize, defaultTextSize);
            mSizeRightText = ta.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, defaultTextSize);

            mLeftMinWidth = ta.getDimensionPixelSize(R.styleable.TitleBar_leftMinWidth, 0);
            mRightMinWidth = ta.getDimensionPixelSize(R.styleable.TitleBar_rightMinWidth, 0);

            ta.recycle();
        }

        initLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        minCenterMargin = mLeftView.getMeasuredWidth();
        minCenterMargin = minCenterMargin > mRightView.getMeasuredWidth() ? minCenterMargin : mRightView.getMeasuredWidth();

        LayoutParams params = (LayoutParams) mCenterView.getLayoutParams();
        params.leftMargin = minCenterMargin - mLeftView.getMeasuredWidth();
        params.rightMargin = minCenterMargin - mRightView.getMeasuredWidth();
        mCenterView.setLayoutParams(params);
    }

    private void initLayout() {
        addLeftButton();
        addRightButton();
        addCenterText();
    }

    private void addLeftButton() {
        LayoutParams params = generateLeftLayoutParams();
        switch (mLeftType) {
            case TYPE_ICON:
                mLeftView = generateImageButtonWithDrawable(mIconLeft);
                break;
            case TYPE_TEXT:
            case TYPE_BOTH:
                // 颜色 icon 大小 文字
                TextView tv = new TextView(getContext());
                tv.setBackgroundColor(Color.TRANSPARENT);
                tv.setGravity(Gravity.CENTER);
                tv.setText(mTextLeft);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeLeftText);
                tv.setTextColor(mColorLeftText);
                tv.setCompoundDrawablesWithIntrinsicBounds(mIconLeft, null, null, null);
                mLeftView = tv;
                break;
            case TYPE_NONE:
            default:
                View v = new View(getContext());
                v.setBackgroundColor(Color.TRANSPARENT);
                params.width = mRightMinWidth;
                mLeftView = v;
                break;
        }
        mLeftView.setId(R.id.title_bar_left);
        mLeftView.setMinimumWidth(mLeftMinWidth);
        addView(mLeftView, params);
    }

    private LayoutParams generateLeftLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_LEFT);
        return params;
    }

    private void addRightButton() {
        LayoutParams params = generateRightLayoutParams();
        switch (mRightType) {
            case TYPE_ICON:
                mRightView = generateImageButtonWithDrawable(mIconRight);
                break;
            case TYPE_TEXT:
                // 颜色 icon 大小 文字
                TextView tv = new TextView(getContext());
                tv.setBackgroundColor(Color.TRANSPARENT);
                tv.setGravity(Gravity.CENTER);
                tv.setText(mTextRight);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeRightText);
                tv.setTextColor(mColorRightText);
                mRightView = tv;
                break;
            case TYPE_NONE:
            default:
                View v = new View(getContext());
                v.setBackgroundColor(Color.TRANSPARENT);
                params.width = mRightMinWidth;
                mRightView = v;
                break;
        }
        mRightView.setId(R.id.title_bar_right);
        mRightView.setMinimumWidth(mRightMinWidth);
        addView(mRightView, params);
    }

    private LayoutParams generateRightLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        return params;
    }

    private void addCenterText() {
        TextView view = new TextView(getContext());
        view.setText(mTextCenter);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setTextColor(mColorCenterText);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSizeCenterText);
        view.setGravity(Gravity.CENTER);
        view.setSingleLine();
        view.setEllipsize(TextUtils.TruncateAt.END);
        mCenterView = view;
        mCenterView.setId(R.id.title_bar_center);
        addView(mCenterView, generateCenterLayoutParams());
    }

    private LayoutParams generateCenterLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(RIGHT_OF, R.id.title_bar_left);
        params.addRule(LEFT_OF, R.id.title_bar_right);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        params.leftMargin = minCenterMargin;
        params.rightMargin = minCenterMargin;
        return params;
    }

    private View generateImageButtonWithDrawable(@NonNull Drawable drawable) {
        ImageButton ib = new ImageButton(getContext());
        ib.setBackgroundColor(Color.TRANSPARENT);
        ib.setImageDrawable(drawable);
        ib.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return ib;
    }

    public void setLeftText(CharSequence text) {
        if (mLeftType == TYPE_TEXT || mLeftType == TYPE_BOTH) {
            ((TextView) mLeftView).setText(text);
        }
    }

    public void setCenterText(CharSequence text) {
        ((TextView) mCenterView).setText(text);
    }

    public void setRightText(CharSequence text) {
        if (mRightType == TYPE_TEXT || mRightType == TYPE_BOTH) {
            ((TextView) mRightView).setText(text);
        }
    }

    public void setLeftClickListener(OnClickListener listener) {
        mLeftView.setOnClickListener(listener);
    }

    public void setCenterClickListener(OnClickListener listener) {
        mCenterView.setOnClickListener(listener);
    }

    public void setRightClickListener(OnClickListener listener) {
        mRightView.setOnClickListener(listener);
    }
}
