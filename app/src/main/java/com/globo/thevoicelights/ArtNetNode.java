package com.globo.thevoicelights;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by deped on 21/12/15.
 */
public class ArtNetNode {

    private native int getR();
    private native int getG();
    private native int getB();
    private native void nativeDisconnect();
    private native void nativeConnect();

    private static ArtNetNode _instance;

    private ArtNetNode() {
    }

    static ArtNetNode instance() {
        if (_instance == null) {
            _instance = new ArtNetNode();
        }
        return _instance;
    }

    static {
        System.loadLibrary("thevoicelights");
    }

    public int getColor() {
        String s = String.format("R %s G %s B %s", this.getR(), this.getG(), this.getB());
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }

    public void connect() {
        nativeConnect();
    }

    public void close() {
        nativeDisconnect();
    }
}
