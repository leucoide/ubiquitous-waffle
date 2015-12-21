//
// Created by deped on 21/12/15.
//

#include <string.h>
#include <jni.h>

jstring Java_com_globo_thevoicelights_MainActivity_stringFromJNI( JNIEnv* env, jobject thiz ){
    return (*env) -> NewStringUTF(env, "Hello");
}