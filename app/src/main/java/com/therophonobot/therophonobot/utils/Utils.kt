package com.therophonobot.therophonobot.utils

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.TimeZone
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun getScreenWidth() : Int {
    return  LocalConfiguration.current.screenWidthDp
}

@Composable
fun getScreenHeight(): Int{
    return  LocalConfiguration.current.screenHeightDp
}

fun getCountryName() : String?{
    val tz: TimeZone = TimeZone.getDefault()
    val displayNameLong: String = tz.getDisplayName(false, TimeZone.LONG)
    return if(displayNameLong.isEmpty()) null else displayNameLong.split(" ")[0]
}

@SuppressLint("SimpleDateFormat")
fun getCurrentTime():String?{
    val dateFormatGmt = SimpleDateFormat("dd:MM:yyyy HH:mm:ss")
    dateFormatGmt.setTimeZone(java.util.TimeZone.getTimeZone("GMT"))
    val gmtTime: String = dateFormatGmt.format(Date())
    return  if(gmtTime.isEmpty()) null else gmtTime
}


fun checkNetwork(context : Context) : Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val network = connectivityManager.activeNetwork?: return false
        val activityNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when{
            activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            else -> false
        }
    }else{
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}