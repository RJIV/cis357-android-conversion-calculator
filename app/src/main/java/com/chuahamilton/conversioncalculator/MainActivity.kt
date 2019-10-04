package com.chuahamilton.conversioncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chuahamilton.conversioncalculator.fragments.ConversionHomeScreen
import com.gvsu.hamilton.conversioncalculator.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ConversionHomeScreen())
                .addToBackStack(null)
                .commit()
        }
    }
}
