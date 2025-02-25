package org.example.project.platform

import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.example.project.di.KoinInjector
import kotlin.getValue

actual fun locationStatusFlow(): Flow<Boolean> = callbackFlow {
    val context by KoinInjector.koin.inject<Context>()
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var isPlay = true
    var temp: Boolean? = null
    CoroutineScope(Dispatchers.IO).launch {
        while (isPlay) {
            // Также можно сразу отправить начальное состояние местоположения
            val new = isLocationEnabled(locationManager)
            if (temp != new) send(new)
            temp = new
            delay(500L)
        }
    }

    awaitClose {
        isPlay = false
    }
}