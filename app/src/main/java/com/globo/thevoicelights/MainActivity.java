package com.globo.thevoicelights;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {

    private int mDelay = 500;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(FLAG_FULLSCREEN,
                    FLAG_FULLSCREEN);
        }
        findViewById(R.id.surface).setBackgroundColor(ArtNetNode.instance().getColor());
      }
}
