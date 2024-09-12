package com.cannadys.core.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class ValuesStoreManager {

    private val settings = Settings()

    var token: String
        get() = settings["token"] ?: ""
        set(value) {
            settings["token"] = value
        }
}