/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_easygoal_achieve_Hello */

#ifndef _Included_com_easygoal_achieve_Hello
#define _Included_com_easygoal_achieve_Hello
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_easygoal_achieve_Hello
 * Method:    sayHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_easygoal_achieve_Hello_sayHello(JNIEnv *env,jclass jobj){
       //返回一句话
       return (*env)->NewStringUTF(env, "亲,开工了,加油哦!");
}

#ifdef __cplusplus
}
#endif
#endif
