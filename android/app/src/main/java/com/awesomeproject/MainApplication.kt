package com.awesomeproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.facebook.react.*
import com.facebook.soloader.SoLoader
import java.lang.reflect.InvocationTargetException

class MainApplication : Application(), ReactApplication {
    private val mReactNativeHost: ReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getPackages(): List<ReactPackage> {
            val packages: MutableList<ReactPackage> = PackageList(this).packages
            // Packages that cannot be autolinked yet can be added manually here, for example:
            // packages.add(new MyReactNativePackage());
            packages.add(MyAppPackage())
            return packages
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this,  /* native exopackage */false)
        initializeFlipper(this, reactNativeHost.reactInstanceManager)
        registerActivityLifecycleCallbacks(LifecycleDetector.activityLifecycleCallbacks)

    }

    companion object {
        /**
         * Loads Flipper in React Native templates. Call this in the onCreate method with something like
         * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
         *
         * @param context
         * @param reactInstanceManager
         */
        private fun initializeFlipper(
                context: Context, reactInstanceManager: ReactInstanceManager) {
            if (BuildConfig.DEBUG) {
                try {
                    /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
                    val aClass = Class.forName("com.awesomeproject.ReactNativeFlipper")
                    aClass
                            .getMethod("initializeFlipper", Context::class.java, ReactInstanceManager::class.java)
                            .invoke(null, context, reactInstanceManager)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
object LifecycleDetector {
    val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks =
            ActivityLifecycleCallbacks()

    var listener: Listener? = null

    var isActivityRunning = false
        private set

    interface Listener {
        fun onReactActivityCreated()
        fun onReactActivityDestroyed()
    }

    private class ActivityLifecycleCallbacks :
            Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(
                activity: Activity, state: Bundle?) {
            if (activity is MainActivity) {
                isActivityRunning = true
                listener?.onReactActivityCreated()
            }
        }

        override fun onActivityResumed(p0: Activity) {
            Log.d("ActivityResumed","stopping Foreground Service")
            BackgroundService.stopMyService(p0)

        }

        override fun onActivityPaused(p0: Activity) {

        }

        override fun onActivityStarted(p0: Activity) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            if (activity is MainActivity) {
                isActivityRunning = false
                listener?.onReactActivityDestroyed()

            }
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

        }

        override fun onActivityStopped(p0: Activity) {

        }

        // Additional overrides omitted for brevity
    }
}