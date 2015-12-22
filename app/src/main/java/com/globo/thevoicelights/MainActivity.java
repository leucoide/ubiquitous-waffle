package com.globo.thevoicelights;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {

    private int mDelay = 1000;
    private final Handler handler = new Handler();
    SurfaceView surface = null;

    ArtNetNode node = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(FLAG_FULLSCREEN,
                    FLAG_FULLSCREEN);
        }
        surface = (SurfaceView) findViewById(R.id.surface);
        node = new ArtNetNode();

        node.connect();

        Timer timer = new Timer();

        MyTimer mt = new MyTimer();

        timer.schedule(mt, 1000, 1000);
    }

    class MyTimer extends TimerTask {

        public void run() {

            runOnUiThread(new Runnable() {

                public void run() {

                    surface.setBackgroundColor(node.getColor());
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private class BackgroundColorTask implements Runnable {

        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        surface.setBackgroundColor(node.getColor());
                        sleep();
                    }
                }
            });
        }
    }

    private void sleep() {
        try {
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
