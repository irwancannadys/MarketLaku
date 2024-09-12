package org.example.core.utils


/**
 * Custom parcelize and parcelable required for Android in Kotlin Multiplatform
 * refer: https://issuetracker.google.com/issues/315775835#comment16
 * */
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
expect annotation class Parcelize()