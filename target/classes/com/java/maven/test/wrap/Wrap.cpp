#include "Wrap.h"
#include <stdlib.h>
#include <string.h>
#include "header_def.cuh"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jbooleanArray JNICALL Java_com_java_maven_test_wrap_Wrap_seqpass(JNIEnv *env, jobject obj,jobjectArray array)
{
	
	jint sizeout = env->GetArrayLength(array);
	jboolean carray[sizeout];
	bool* narray;
	const char *stringholder[sizeout];
	
	jint i;
	for(i = 0; i < sizeout;i++)//there is no standard function to convert java array of strings(which are seen as objects) to a native array of strings
	{
		 jstring str = (jstring) env->GetObjectArrayElement( array, i);//convert a jobject in an array to a jstring
		 stringholder[i] = env->GetStringUTFChars( str, 0);		
	}
	//jstring *sholder = (*env)->GetBooleanArrayElements(env, array, 0); //
	printf("\nJNI: going to CUDA");
	narray = mymain((int)sizeout, stringholder); // DH->ERIC: pass sizeout as an int, stringholder as a char**
	printf("\nJNI: back in JNI");
	//printf("\n im in cpp");
	
	for(i = 0; i < sizeout;i++)//cast all native booleans to jboolean
	{
		carray[i] = (jboolean)narray[i];
		
		printf("\nresult %i is: %d", i,  narray[i]);
	}
	
	//each element in the array is true if the sequence made it through filter
	jbooleanArray boolholder = env->NewBooleanArray(sizeout);  
	env->SetBooleanArrayRegion( boolholder, 0, sizeout, carray);
	
	// DH->ERIC: Free the manually allocated memory once finished.
	//free(carray);
//printf("\n leaving cpp");
	return boolholder;
}

#ifdef __cplusplus
}
#endif

