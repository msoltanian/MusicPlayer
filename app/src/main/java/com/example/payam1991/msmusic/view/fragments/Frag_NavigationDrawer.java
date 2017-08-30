package com.example.payam1991.msmusic.view.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.payam1991.msmusic.R;
import com.example.payam1991.msmusic.view.activities.AboutActivity;

/**
 * Created by msoltanian on 8/30/2017.
 */
public class Frag_NavigationDrawer extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    Toolbar act_toolbar;
    private boolean mFromSavedInstanceState;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_navigation_drawer, container, false);
        return view;
    }


    public void setUp(int fragmentID, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        act_toolbar = toolbar;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerItemClickListener(containerView);
        if (!mFromSavedInstanceState) {
            mDrawerLayout.closeDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeSelected();
            }
        });
    }

    public void onHomeSelected() {
        if (mDrawerLayout.isDrawerOpen(containerView)) {
            mDrawerLayout.closeDrawer(containerView);
        } else {
            mDrawerLayout.openDrawer(containerView);
        }

    }

    public void drawerItemClickListener(View v) {
        RelativeLayout rlAbout = (RelativeLayout) v.findViewById(R.id.rl_about);
        rlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), AboutActivity.class));
                mDrawerLayout.closeDrawer(containerView);
            }
        });
    }
}
