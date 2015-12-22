package com.globo.thevoicelights;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by deped on 21/12/15.
 */
public class ArtNetNode {

    private final SurfaceView view;

    private synchronized native int getR();
    private synchronized native int getG();
    private synchronized native int getB();
    private native void nativeDisconnect();
    private native int nativeConnect();
    private native void readArtNet();

    private ColorPoll colorPoll;

    private static ArtNetNode _instance;

    public ArtNetNode(SurfaceView view) {
        this.colorPoll = new ColorPoll();
        this.view = view;
    }


    static ArtNetNode instance(SurfaceView view) {
        if (_instance == null) {
            _instance = new ArtNetNode(view);
        }
        return _instance;
    }

    static {
        System.loadLibrary("thevoicelights");
    }

    public int getColor() {
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }

    public void connect(){
        this.nativeConnect();
        this.colorPoll = new ColorPoll();
        this.colorPoll.execute();
    }
    public void disconnect(){
        this.nativeDisconnect();
        this.colorPoll.cancel(false);

    }
    public void print(int color){
        this.view.setBackgroundColor(color);
    }
    private class ColorPoll extends AsyncTask<Void,Integer,Void>{

        public Integer currentColor = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            while(!isCancelled()){
                ArtNetNode.this.readArtNet();
                publishProgress(ArtNetNode.this.getColor());

            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            if(!currentColor.equals(values[0])){
                currentColor = values[0];
                ArtNetNode.this.print(currentColor);
            }

        }
    }

}
