package com.bwie.week1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private WaveView wv;
    private ImageView imgCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv = findViewById(R.id.wv);
        imgCursor = findViewById(R.id.img_cursor);


        WaveView.OnWaveChangeListener listener = new WaveView.OnWaveChangeListener() {
            @Override
            public void onChanged(float y) {

                //Log.i(TAG，"onChanged: y=" + y);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgCursor.getLayoutParams();
                layoutParams.setMargins(0,0,0,(int) y);
                imgCursor.setLayoutParams(layoutParams);
            }
        };
        wv.setOnWaveChangeListener(listener);
        //会调用onMeasure和onLayout方法
       // wv.requestLayout();
    }
}
