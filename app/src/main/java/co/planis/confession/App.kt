package co.planis.confession

import android.app.Application
import com.facebook.FacebookSdk
import timber.log.Timber

/**
 * Created by janpluta on 12.10.16.
 */
class App : Application() {


    override fun onCreate() {

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        super.onCreate()
    }
}