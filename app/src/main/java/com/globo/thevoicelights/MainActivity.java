package com.globo.thevoicelights;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

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


//        ArtNetNode.instance().connect();

//        Timer timer = new Timer();
//
//        MyTimer mt = new MyTimer();
//
//        timer.schedule(mt, 1000, 1000);
    }

    public void logClick(View view) {
        Log.d("THE-CLICK", "Clicked");
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


    @Override
    protected void onResume() {
        super.onResume();
        ArtNetNode.instance(surface).connect();

//        (new ColorService()).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ArtNetNode.instance(surface).disconnect();
    }

//    private class ColorService extends AsyncTask<Void,Void,Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            while (true) {
//                publishProgress();
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
////            Log.d("Main", String.format("changing color %d", ArtNetNode.instance().getColor()));
////            findViewById(R.id.surface).setBackgroundColor(ArtNetNode.instance().getColor());
//        }
//    }
}
