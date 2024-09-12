package org.example.project

import com.cannadys.core.CoreModule
import com.cannadys.data.DataModule

object ModuleInstaller {

    fun install() {
        CoreModule.init()
        DataModule.init()
//        AuthenticationModule.init()
//        HomeModule.init()
//        TopUpModule.init()
    }
}