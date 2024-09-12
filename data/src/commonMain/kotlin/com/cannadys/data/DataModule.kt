package com.cannadys.data

import com.cannadys.core.di.InstancesManager

object DataModule {

    fun init() = with(InstancesManager) {
//        install { UserRepository(it.get(), it.get()) }
//        install { ProductRepository(it.get()) }
//        install { TransactionRepository(it.get()) }
//        install { TopUpRepository(it.get()) }
    }
}