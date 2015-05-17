package com.petrodevelopment.copdapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.util.ViewAttrsUtil;

/**
 * Created by Andrey Petrov on 15-05-14.
 */
public class TabsView extends LinearLayout {

    private int mCurrentPosition = 0;
    private OnTabSelectedListener mOnTabSelectedListener;

    public interface OnTabSelectedListener {
        void onTabSelected(int position, View view);
    }
    public TabsView(Context context, int selectedPosition, OnTabSelectedListener onTabSelectedListener) {
        super(context);
        mOnTabSelectedListener = onTabSelectedListener;
        mCurrentPosition = selectedPosition;
        initialize(context);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
    }

    public TabsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewAttrsUtil viewAttrsUtil = new ViewAttrsUtil(context, attrs, R.styleable.TabsView);
        mCurrentPosition = viewAttrsUtil.getIntFromXml(R.styleable.TabsView_selected, 0);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initButtonListeners();
        select(mCurrentPosition);
    }

    private void initButtonListeners() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final int j = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.log(this, "item selected");
                    select(j);
                }
            });
        }
    }

    /**
     * Choose item at the given position and notify the listener if there is one
     * @param newPosition
     */
    public void select(int newPosition) {
        mCurrentPosition = newPosition;
        U.log(this, "selecting position: " + mCurrentPosition);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (i == mCurrentPosition) getChildAt(i).setSelected(true);
            else getChildAt(i).setSelected(false);
        }
        if (mOnTabSelectedListener != null) mOnTabSelectedListener.onTabSelected(mCurrentPosition, getChildAt(mCurrentPosition));
    }
}
