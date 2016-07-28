package com.didi.slidingmenu;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by TIAN on 2016/4/16.
 */
public class MyDrawerLayout extends DrawerLayout {

    public MyDrawerLayout(Context context) {
        this(context, null);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private int mTouchSlop;
    private float mLastMotionX;
    private float mLastMotionY;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            final float x = ev.getX();
            final float y = ev.getY();

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastMotionX = x;
                    mLastMotionY = y;
                    break;

                case MotionEvent.ACTION_MOVE:
                    int xDiff = (int) Math.abs(x - mLastMotionX);
                    int yDiff = (int) Math.abs(y - mLastMotionY);
                    final int x_yDiff = xDiff * xDiff + yDiff * yDiff;

                    boolean xMoved = x_yDiff > mTouchSlop * mTouchSlop;

                    if (xMoved) {
                        if (xDiff > yDiff * 4) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    break;
                default:

                    break;
            }
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }
}
