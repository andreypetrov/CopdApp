package com.petrodevelopment.copdapp.views;

import com.petrodevelopment.copdapp.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class Line extends CustomizableView {
    private static final String HORIZONTAL = "horizontal";
    private static final String VERTICAL = "vertical";

    private String mOrientation;

    private Paint mLinePaint;

    public Line(Context context) {
        super(context);
        init();
    }

    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setStylableResourceArray(R.styleable.Line);

        mLinePaint = new Paint();
        mLinePaint.setColor(getColorFromXml(R.styleable.Line_line_color));
        mLinePaint.setStrokeWidth(getFloatFromXml(R.styleable.Line_line_stroke, 0.0f));
        mLinePaint.setAntiAlias(true);
        mOrientation = getStringFromXml(R.styleable.Line_line_orientation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerY = this.getMeasuredHeight() / 2;
        int centerX = this.getMeasuredWidth() / 2;

        switch (mOrientation) {
            case HORIZONTAL:
                drawHorizontal(centerY, canvas);
                break;
            case VERTICAL:
                drawVertical(centerX, canvas);
                break;
            default:
                drawHorizontal(centerY, canvas);
                break;
        }

    }

    private void drawHorizontal(int centerY, Canvas canvas) {
        canvas.drawLine(0, centerY, this.getMeasuredWidth(), centerY, mLinePaint);
    }

    private void drawVertical(int centerX, Canvas canvas) {
        canvas.drawLine(centerX, 0, centerX, this.getMeasuredHeight(), mLinePaint);
    }

}
