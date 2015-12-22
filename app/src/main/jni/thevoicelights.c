#include <jni.h>
#include <time.h>
#include <stdlib.h>
#include "artnet.h"
#include "packets.h"
#include <android/log.h>

#define MODULE_NAME  "THE-VOICE-LIGHTS"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, MODULE_NAME, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, MODULE_NAME, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, MODULE_NAME, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,MODULE_NAME, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,MODULE_NAME, __VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,MODULE_NAME, __VA_ARGS__)

artnet_node *artnetNode;

int dmxR = 0;
int dmxG = 0;
int dmxB = 0;

long random_at_most(long max) {
    unsigned long
    // max <= RAND_MAX < ULONG_MAX, so this is okay.
            num_bins = (unsigned long) max + 1,
            num_rand = (unsigned long) RAND_MAX + 1,
            bin_size = num_rand / num_bins,
            defect = num_rand % num_bins;

    long x;
    do {
        x = random();
    }
        // This is carefully written not to overflow
    while (num_rand - defect <= (unsigned long) x);

    // Truncated division is intentional
    return x / bin_size;
}

int receiver(artnet_node node, void *pp, void *d) {
    artnet_packet pack = (artnet_packet) pp;



    dmxR = pack->data.admx.data[0];
    dmxG = pack->data.admx.data[1];
    dmxB = pack->data.admx.data[2];

    LOGV("Got RGB: ==>  R: %d G: %d B: %d", dmxR, dmxG, dmxB);

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getR(JNIEnv *env, jobject instance) {
    //LOGV("Obtendo R:%d", dmxR);
    return dmxR;
//    return random_at_most(255);
}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getG(JNIEnv *env, jobject instance) {
    //LOGV("Obtendo G:%d", dmxG);
    return dmxG;
//    return random_at_most(255);
}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getB(JNIEnv *env, jobject instance) {
    //LOGV("Obtendo B:%d", dmxB
    return dmxB;
//    return random_at_most(255);
}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeConnect(JNIEnv *env, jobject instance) {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    char *ip_addr = NULL;

    uint8_t subnet_addr = 0;
    uint8_t port_addr = 1;

    artnetNode = artnet_new(ip_addr, 1);

    if (!artnetNode) {
        return;
    }

    artnet_set_long_name(artnetNode, "Art-Net Test");
    artnet_set_short_name(artnetNode, "ANT");

    // set the upper 4 bits of the universe address
    artnet_set_subnet_addr(artnetNode, subnet_addr);

    // enable port 0
    artnet_set_port_type(artnetNode, 0, ARTNET_ENABLE_OUTPUT, ARTNET_PORT_DMX);

    // bind port 0 to universe 1
    artnet_set_port_addr(artnetNode, 0, ARTNET_OUTPUT_PORT, port_addr);

    artnet_dump_config(artnetNode);

    artnet_set_handler(artnetNode, ARTNET_RECV_HANDLER, receiver, NULL);


    if (artnet_start(artnetNode) != 0) {
        LOGV("not connected!");
        return;
    }


    LOGV("connected!");


}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeDisconnect(JNIEnv *env, jobject instance) {
    artnet_stop(artnetNode);
    artnet_destroy(artnetNode);
    LOGV("disconnected!");
}


JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_readArtNet(JNIEnv *env, jobject instance) {

    artnet_read(artnetNode, 0);
    
}