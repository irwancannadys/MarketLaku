package com.cannadys.core

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual typealias WeakReferenceCommon<T> = kotlin.native.ref.WeakReference<T>