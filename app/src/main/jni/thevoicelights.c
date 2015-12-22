#include <jni.h>
#include "artnet.h"
#include "packets.h"

artnet_node *artnetNode;

int dmxR=255;
int dmxG=100;
int dmxB=50;

int receiver(artnet_node node, void *pp, void *d) {
    artnet_packet pack = (artnet_packet) pp;
    dmxR=pack->data.admx.data[0];
    dmxG=pack->data.admx.data[1];
    dmxB=pack->data.admx.data[2];
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getR(JNIEnv *env, jobject instance) {

    return dmxR;
}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getG(JNIEnv *env, jobject instance) {

    return dmxG;

}

JNIEXPORT jint JNICALL
Java_com_globo_thevoicelights_ArtNetNode_getB(JNIEnv *env, jobject instance) {

    return dmxB;

}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeConnect(JNIEnv *env, jobject instance) {
    char *ip_addr = NULL;
    uint8_t subnet_addr = 0;
    uint8_t port_addr = 1;
    artnetNode = artnet_new(ip_addr, 1);
    artnet_set_long_name(artnetNode, "Art-Net Test");
    artnet_set_short_name(artnetNode, "ANT");
    artnet_set_subnet_addr(artnetNode, subnet_addr) ;
    artnet_set_port_type(artnetNode, 0, ARTNET_ENABLE_OUTPUT, ARTNET_PORT_DMX) ;
    artnet_set_port_addr(artnetNode, 0, ARTNET_OUTPUT_PORT, port_addr);
    artnet_dump_config(artnetNode);
    artnet_set_handler(artnetNode, ARTNET_RECV_HANDLER, receiver, NULL);
}

JNIEXPORT void JNICALL
Java_com_globo_thevoicelights_ArtNetNode_nativeDisconnect(JNIEnv *env, jobject instance) {
    artnet_stop(artnetNode);
    artnet_destroy(artnetNode);
}