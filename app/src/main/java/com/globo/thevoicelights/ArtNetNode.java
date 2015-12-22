package com.globo.thevoicelights;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by deped on 21/12/15.
 */
public class ArtNetNode {

    private synchronized native int getR();
    private synchronized native int getG();
    private synchronized native int getB();
    private native void nativeDisconnect();
    private native int nativeConnect();
    private native void nativePerformBackgroundTask();

    private static ArtNetNode _instance;

    public ArtNetNode() {
    }

//    static ArtNetNode instance() {
//        if (_instance == null) {
//            _instance = new ArtNetNode();
//        }
//        return _instance;
//    }

    static {
        System.loadLibrary("thevoicelights");
    }

    public int getColor() {
//        String s = String.format("R %s G %s B %s", this.getR(), this.getG(), this.getB());
//        Log.d("RGB------>" , s);
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }

    public void connect(){ this.nativeConnect();}
    public void disconnect(){ this.nativeDisconnect();}

    public void close() {
        nativeDisconnect();
    }

    public void performBackgroundTask(){
        nativePerformBackgroundTask();
    }
}
