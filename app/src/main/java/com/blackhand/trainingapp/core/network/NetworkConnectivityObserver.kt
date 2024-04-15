package com.blackhand.trainingapp.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(private val context: Context) : ConnectivityObserver {

    /**
     * Todo this code to check internet connection you should check if internet available get data from api service else get data from cache(RoomDB)
     */

    // define connect manager
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.NetworkStatus> {
        return callbackFlow {

            val activeNetwork = connectivityManager?.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) {
                launch {
                    send(ConnectivityObserver.NetworkStatus.Available)
                }
            } else {
                launch {
                    send(ConnectivityObserver.NetworkStatus.Unavailable)
                }
            }

            val callBack = object : ConnectivityManager.NetworkCallback() {

                @Override
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(ConnectivityObserver.NetworkStatus.Available)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(ConnectivityObserver.NetworkStatus.Unavailable)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(ConnectivityObserver.NetworkStatus.Lost)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(ConnectivityObserver.NetworkStatus.Losing)
                    }
                }
            }
            connectivityManager?.registerDefaultNetworkCallback(callBack)
            awaitClose {
                connectivityManager?.unregisterNetworkCallback(callBack)
            }
        }.distinctUntilChanged()
    }
}