package com.globo.thevoicelights;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity implements ArtNetNodeDelegate {

    SurfaceView surface = null;
    ImageView splashScreen = null;

    private static final String TAG = MainActivity.class.getSimpleName();

    PowerManager.WakeLock wakeLock;
    private Integer currentColor = 0;
    private boolean showingSplashScreen = true;
    private Animation fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(FLAG_FULLSCREEN,
                    FLAG_FULLSCREEN);
        }
        this.surface = (SurfaceView) findViewById(R.id.surface);
        this.splashScreen = (ImageView) findViewById(R.id.splash_screen);
        this.fade = AnimationUtils.loadAnimation(this, R.anim.splash_screen_fade);

        this.fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashScreen.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        this.wakeLock.acquire();
        ArtNetNode.instance(this).connect();


    }

    @Override
    protected void onPause() {
        super.onPause();
        this.wakeLock.release();
        ArtNetNode.instance(this).disconnect();

    }


    @Override
    public void gotPackage(Integer color) {
        if (this.showingSplashScreen) {
            splashScreen.startAnimation(this.fade);
            this.showingSplashScreen = false;
        }
        if (!this.currentColor.equals(color)) {
            this.currentColor = color;
            this.print();
        }
    }

    public void print() {
        Log.d(TAG, "print() called with: " + this.currentColor);
        this.surface.setBackgroundColor(this.currentColor);
    }
}
