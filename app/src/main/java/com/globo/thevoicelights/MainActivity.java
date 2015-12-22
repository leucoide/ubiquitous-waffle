package com.globo.thevoicelights;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        ArtNetNode.instance().connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(FLAG_FULLSCREEN,
                    FLAG_FULLSCREEN);
        }


      }

    @Override
    protected void onResume() {
        super.onResume();
        ArtNetNode.instance().connect();
        (new ColorService()).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ArtNetNode.instance().disconnect();
    }

    private class ColorService extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            findViewById(R.id.surface).setBackgroundColor(ArtNetNode.instance().getColor());
        }
    }
}
