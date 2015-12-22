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
//        String s = String.format("R %s G %s B %s", this.getR(), this.getG(), this.getB());
//        Log.d("RGB------>" , s);
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }

    public void connect(){
        this.nativeConnect();
        this.colorPoll.execute();
    }
    public void disconnect(){
        this.nativeDisconnect();
        this.colorPoll.cancel(true);
    }
    public void print(int color){
        this.view.setBackgroundColor(color);
    }
    private class ColorPoll extends AsyncTask<Void,Integer,Void>{

        private Integer currentColor = 0;

        @Override
        protected Void doInBackground(Void... voids) {

            while(true){
                ArtNetNode.this.readArtNet();
                publishProgress(ArtNetNode.this.getColor());
            }

//            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(!currentColor.equals(values[0])){
                currentColor = values[0];
                ArtNetNode.this.print(currentColor);
                Log.d("THE-", "Changing color");
            }else {
                Log.d("THE-", "Not changing color");
            }

        }
    }

}
