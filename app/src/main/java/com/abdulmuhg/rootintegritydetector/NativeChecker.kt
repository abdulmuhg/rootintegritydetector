package com.abdulmuhg.rootintegritydetector

object NativeChecker {
    init { System.loadLibrary("native-lib") }
    external fun nativeCheck(): String
}
