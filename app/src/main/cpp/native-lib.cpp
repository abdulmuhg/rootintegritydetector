#include <jni.h>
#include <string>
#include <sys/stat.h>
#include <sys/system_properties.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_abdulmuhg_rootintegritydetector_NativeChecker_nativeCheck(JNIEnv* env, jobject /*thiz*/) {
    const char* su_path = "/system/xbin/su";
    struct stat buffer{};
    bool found = (stat(su_path, &buffer) == 0);

    char val[PROP_VALUE_MAX];
    __system_property_get("ro.debuggable", val);
    std::string prop = val;

    std::string result = std::string("suFound=") + (found ? "true" : "false") +
                         ", ro.debuggable=" + prop;
    return env->NewStringUTF(result.c_str());
}
