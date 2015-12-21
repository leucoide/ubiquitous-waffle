#include <jni.h>

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getR(JNIEnv *env, jobject instance) {

    return 255;

}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getG(JNIEnv *env, jobject instance) {

    return 100;

}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getB(JNIEnv *env, jobject instance) {

    return 200;

}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeConnect(JNIEnv *env, jobject instance) {

    // TODO

}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeDisconnect(JNIEnv *env, jobject instance) {

    // TODO

}