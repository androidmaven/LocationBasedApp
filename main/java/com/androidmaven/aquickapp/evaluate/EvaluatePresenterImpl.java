package com.androidmaven.aquickapp.evaluate;

import android.animation.ValueAnimator;

public class EvaluatePresenterImpl implements EvaluatePresenter {
    EvaluateInterface evaluateInterface;

    public EvaluatePresenterImpl(EvaluateInterface evaluateInterface) {
        this.evaluateInterface = evaluateInterface;
    }

    @Override
    public void startWork(double lat1, double long1, double lat2, double long2) {

        final ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, new Double(distance(lat1,lat2,long1,long2,"M")).intValue());// here you set the range, from 0 to "count" value
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                evaluateInterface.animationCounter(animator);
            }
        });
        animator.setDuration(1000); // here you set the duration of the anim
        animator.start();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit.equals("K")) {
            dist = dist * 1.609344;
        } else if (unit.equals("N")) {
            dist = dist * 0.8684;
        }
        return (dist);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
