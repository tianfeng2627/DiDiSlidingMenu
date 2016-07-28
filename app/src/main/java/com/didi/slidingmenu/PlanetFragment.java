package com.didi.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.Timer;
import java.util.TimerTask;

public class PlanetFragment extends Fragment {

    private String TAG = "PlanetFragment";

    public static final String ARG_PLANET_NUMBER = "planet_number";
    private int mTopFlHeight;
    private int mTopFlWidth;
    private FrameLayout mTopFramLayout;

    private View mPhoneText;

    private int mPhoneTextHeight;

    private int mPhoneTextWidth;

    public PlanetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        final View listIv = (View) rootView.findViewById(R.id.list);
        mPhoneText = (View) rootView.findViewById(R.id.user_phone);
        final ImageView arrow = (ImageView) rootView.findViewById(R.id.newsidebar_arrow);
        mTopFramLayout = (FrameLayout) rootView.findViewById(R.id.top_fl);
        final ImageView topLevelIcon = (ImageView) rootView.findViewById(R.id.user_level);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                mTopFlHeight = mTopFramLayout.getHeight();
                mTopFlWidth = mTopFramLayout.getWidth();
                mPhoneTextHeight = mPhoneText.getHeight();
                mPhoneTextWidth = mPhoneText.getWidth();
            }
        }, 500);
        SlidingUpPanelLayout sldingUpPanelLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        sldingUpPanelLayout.setShadowHeight(0);
        final float anchorPoint = 1;
        sldingUpPanelLayout.setAnchorPoint(anchorPoint);

        sldingUpPanelLayout.addPanelSlideListener(new PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//				Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                float listAlpha = (1 - slideOffset / anchorPoint);
                listIv.setAlpha(listAlpha);
//				Log.d(TAG, "middle alpha=" + listAlpha);
                float topLevelIconAlpha = (1 - slideOffset / anchorPoint);
                topLevelIcon.setAlpha(topLevelIconAlpha);
//				Log.d(TAG, "top alpha=" + topLevelIconAlpha);
                LayoutParams topFlLayoutParams = mTopFramLayout.getLayoutParams();
                topFlLayoutParams.height = (int) (mTopFlHeight * (1 - slideOffset / 2));
                topFlLayoutParams.width = (int) (mTopFlWidth * (1 - slideOffset / 2));
                mTopFramLayout.setLayoutParams(topFlLayoutParams);
                LayoutParams topPhoneTvLayoutParams = mPhoneText.getLayoutParams();
                topPhoneTvLayoutParams.height = (int) (mPhoneTextHeight
                        * ((slideOffset) > anchorPoint ? 0 : (1 - slideOffset / anchorPoint)));
                topPhoneTvLayoutParams.width = (int) (mPhoneTextWidth);
                mPhoneText.setLayoutParams(topPhoneTvLayoutParams);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (newState == PanelState.EXPANDED) {
                    arrow.setImageResource(R.drawable.newsidebar_arrow_down);
                } else if (newState == PanelState.COLLAPSED) {
                    arrow.setImageResource(R.drawable.newsidebar_arrow_up);
                }

            }
        });
        return rootView;
    }
}
