package com.awesomeproject


import android.os.Bundle
import android.util.Log
import com.facebook.react.ReactActivity

class MainActivity : ReactActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Notifications.createNotificationChannels(this)
    }

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    override fun getMainComponentName(): String? {
        return "AwesomeProject"
    }
}