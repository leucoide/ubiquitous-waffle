package com.globo.thevoicelights;

import android.graphics.Color;

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

    private ArtNetNode(){}

    static ArtNetNode instance(){
        if (_instance == null) {
            _instance = new ArtNetNode();
        }
        return _instance;
    }

    static {
        System.loadLibrary("thevoicelights");
    }

    public int getColor(){
        return Color.rgb(this.getR(), this.getG(), this.getB());
    }
    public void connect(){ this.nativeConnect();}
    public void disconnect(){ this.nativeDisconnect();}

}
