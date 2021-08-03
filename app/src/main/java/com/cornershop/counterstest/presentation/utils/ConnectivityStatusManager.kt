package com.cornershop.counterstest.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import javax.inject.Inject

class ConnectivityStatusManager @Inject constructor(context: Context) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val availableNetworks = mutableSetOf<Network>()

    init {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (!availableNetworks.contains(network)) {
                    availableNetworks.add(network)
                }
            }

            override fun onLost(network: Network) {
                availableNetworks.remove(network)
            }
        })
    }

    fun hasNetwork(): Boolean = availableNetworks.isNotEmpty()
}