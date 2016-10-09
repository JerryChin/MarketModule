//
// Created by Xiong on 2016/9/19.
//
#include "assets.h"

static const jbyte key[] = {253,67,82,95,79};

jbyteArray JNICALL Decode(JNIEnv *env,jclass clazz,jbyteArray bytes){
    int len = env->GetArrayLength(bytes);
    const int keyLen = 5;

    jbyte *jb = env->GetByteArrayElements(bytes,JNI_FALSE);

    for(int i = 0;i < len;i++){
        jb[i] ^= key[i%keyLen];
    }

    env->SetByteArrayRegion(bytes,0,len,jb);

    return bytes;
}

