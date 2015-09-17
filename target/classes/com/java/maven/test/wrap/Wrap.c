#include "Wrap.h"
#include <string.h>

JNIEXPORT jobjectArray JNICALL Java_Wrap_seqpass(JNIEnv *env, jobject obj, jobjectArray array)
{
	int size = 30, sizeout = 6;
	//jsize len = env->GetArrayLength(array);

	//make a new array to pass elements that made it through the filter
	const char* beans = "hackerz";
	
	jclass stringClass = (*env)->FindClass(env, "java/lang/String");
    jobjectArray strholder = (*env)->NewObjectArray(env, 6, stringClass, 0);

	
	//jobjectArray result = (jobjectArray)env->NewObjectArray(env,  env->FindClass("java/lang/String"),  env->NewStringUTF(beans));
	
	//jstring jstr[size];
	
	for(int i = 0; i < sizeout;i++)//do something here
	{
		env->SetObjectArrayElement(env,strholder,i,result,i,env->NewStringUTF((jstring)array[i])); 
	}
	
	return strholder;
}

int main(){ return 0;}
