package com.cannadys.core

import com.cannadys.core.di.InstancesManager
import com.cannadys.core.local.ValuesStoreManager
import com.cannadys.core.network.NetworkClient
import org.example.core.result.SharedResultManager

object CoreModule {

    fun init() = with(InstancesManager) {
        install { ValuesStoreManager() }
        install {
            val baseUrl = "https://ppob-fake-api.fly.dev"
            NetworkClient(baseUrl, it.get())
        }
        install { SharedResultManager() }
    }
}