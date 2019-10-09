package com.chuahamilton.conversioncalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.chuahamilton.conversioncalculator.fragments.ConversionHomeScreen
import com.chuahamilton.conversioncalculator.fragments.SettingsFragment
import com.gvsu.hamilton.conversioncalculator.R


class MainActivity : AppCompatActivity() {

    private var conversionType = "Length"

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val bundle = Bundle()
        bundle.putString("key", conversionType)
        val settingsFragment = SettingsFragment()
        settingsFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, settingsFragment)
            .addToBackStack(null)
            .commit()
        return super.onOptionsItemSelected(item)
    }

    fun updateConversionType(conversionType: String) {
        this.conversionType = conversionType
    }

    fun getConversionType(): String {
        return conversionType
    }
}
