package com.globo.thevoicelights;

import android.graphics.Color;
import android.os.AsyncTask;

/**
 * Created by deped on 21/12/15.
 */
public class ArtNetNode {

//    private final SurfaceView view;

    private static ArtNetNode _instance;

    static {
        System.loadLibrary("thevoicelights");
    }

    private ColorPoll colorPoll;
    private ArtNetNodeDelegate delegate;

    public ArtNetNode(ArtNetNodeDelegate delegate) {
        this.colorPoll = new ColorPoll();
        this.delegate = delegate;
    }

    static ArtNetNode instance(ArtNetNodeDelegate delegate) {
        if (_instance == null) {
            _instance = new ArtNetNode(delegate);
        }
        return _instance;
    }

    private synchronized native int getR();

    private synchronized native int getG();

    private synchronized native int getB();

    private native void nativeDisconnect();

    private native int nativeConnect();

    private native void readArtNet();

    private native boolean gotFirstPackage();

    public int getColor() {
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }

    public void connect() {
        this.nativeConnect();
        this.colorPoll = new ColorPoll();
        this.colorPoll.execute();
    }

    public void disconnect() {
        this.nativeDisconnect();
        this.colorPoll.cancel(false);

    }

    private class ColorPoll extends AsyncTask<Void, Integer, Void> {

//        public Integer currentColor = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            while (!isCancelled()) {
                ArtNetNode.this.readArtNet();
                if (ArtNetNode.this.gotFirstPackage()) {
                    publishProgress(ArtNetNode.this.getColor());
                }
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            ArtNetNode.this.delegate.gotPackage(values[0]);

        }
    }

}
