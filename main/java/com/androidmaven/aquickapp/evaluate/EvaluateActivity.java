package com.androidmaven.aquickapp.evaluate;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.androidmaven.aquickapp.R;

public class EvaluateActivity extends AppCompatActivity implements EvaluateInterface {
    double lat1,long1, lat2, long2;
    TextView score;
    EvaluatePresenter evaluatePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate_layout);
        findViewById(R.id.backFrom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        score = findViewById(R.id.textCounter);
        evaluatePresenter = new EvaluatePresenterImpl(this);

        lat1 = getIntent().getExtras().getDouble("lat1");
        lat2 = getIntent().getExtras().getDouble("lat2");
        long1 = getIntent().getExtras().getDouble("long1");
        long2 = getIntent().getExtras().getDouble("long2");

        evaluatePresenter.startWork(lat1,lat2,long1,long2);

    }

    @Override
    public void animationCounter(ValueAnimator animation) {
        score.setText(String.valueOf(animation.getAnimatedValue()));
    }
}
