package com.awesomeproject


import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.app.Activity

class ForegroundModule internal constructor(mycontext: ReactApplicationContext?) : ReactContextBaseJavaModule(mycontext) {
    override fun getName(): String {
        return "ForegroundModule"
    }

    @ReactMethod
    fun executeForegroundService(name: String, location: String) {
        var myActivity: Activity? = currentActivity
        myActivity?.moveTaskToBack(true)
        BackgroundService.startService(this.reactApplicationContext,1)
    }
}