#include "Wrap.h"
#include <string.h>

JNIEXPORT jobjectArray JNICALL Java_Wrap_seqpass(JNIEnv *env, jobject obj, jobjectArray array)
{
	int sizeout = 8;
	
	//make a new array to pass elements that made it through the filter
    jobjectArray strholder =(jobjectArray)env->NewObjectArray(sizeout,  env->FindClass("java/lang/String"),  env->NewStringUTF(""));  

	for(int i = 0; i < sizeout;i++)//do something here
	{
		if(i >3)
		{
			env->SetObjectArrayElement(strholder,i,env->NewStringUTF("sample output")); 
		}
		else
		{
			jstring string = (jstring) env->GetObjectArrayElement( array, i);//convert a jobject in an array to a jstring
			const char *temp = env->GetStringUTFChars( string, 0); // convert a jstring to a native string
			env->SetObjectArrayElement(strholder,i,env->NewStringUTF(temp)); //set the native string to the output object array
		}		
	}	
	//I dont think we need to garbage collect
	return strholder;
}

