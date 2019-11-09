package com.chuahamilton.conversioncalculator

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class ConversionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}