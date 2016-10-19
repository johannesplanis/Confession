package co.planis.confession

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by janpluta on 19.10.16.
 */


fun ConnectivityManager.isConnected(): Boolean{
    return this.activeNetworkInfo?.isConnected ?: false
}


