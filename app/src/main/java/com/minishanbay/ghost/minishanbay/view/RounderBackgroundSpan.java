package com.minishanbay.ghost.minishanbay.view;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.minishanbay.ghost.minishanbay.R;

/**
 * Created by Ghost on 2016/3/28.
 */
public class RounderBackgroundSpan extends ReplacementSpan {
    private static int CORNER_RADIUS = 8;
    private int backgroundColor = 0;
    private int textColor = 0;

    public RounderBackgroundSpan() {
        super();
        backgroundColor = Color.parseColor("#209e85");
        textColor = Color.WHITE;
    }

    public RounderBackgroundSpan(boolean t) {
        super();
        backgroundColor = Color.TRANSPARENT;
        textColor = Color.BLACK;
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
