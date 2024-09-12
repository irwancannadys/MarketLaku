package com.cannadys.core.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual val Double.currency: String get() {
    val numberFormat = NSNumberFormatter()
    numberFormat.locale = NSLocale("id_ID")
    numberFormat.numberStyle = 2u /** source: https://developer.apple.com/documentation/foundation/numberformatter/style/currency */
    return numberFormat.stringFromNumber(NSNumber(this)) ?: ""
}