//
// Created by Xiong on 2016/9/19.
//
#include "initializer.h"
#include <stdio.h>
#include "assets.h"


static const JNINativeMethod gMethods[] = { //定义批量注册的数组，是注册的关键部分
{"decode", "([B)[B", (jbyteArray)Decode}
};

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv *env;
    if(vm->GetEnv((void**)&env,JNI_VERSION_1_4) != JNI_OK){
        return -1;
    }else{
        jclass clazz;
        clazz = env->FindClass("com/hc/library/util/Assets");
        if(clazz == NULL){
            return -1;
        }
        if(env->RegisterNatives(clazz,gMethods, sizeof(gMethods)/sizeof(gMethods[0]))!= JNI_OK)
            //把本地函数和一个java类方法关联起来
        {
          return -1;
        }
    }

    return JNI_VERSION_1_4;
}