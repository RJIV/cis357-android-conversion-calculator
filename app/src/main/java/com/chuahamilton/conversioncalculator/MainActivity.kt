package com.chuahamilton.conversioncalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chuahamilton.conversioncalculator.fragments.ConversionHomeScreen
import com.chuahamilton.conversioncalculator.fragments.SettingsFragment
import com.gvsu.hamilton.conversioncalculator.R


class MainActivity : AppCompatActivity() {

    private var conversionType = "Length"
    private var inSettingsFragment = false
    private lateinit var settingsMenu: Menu

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

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is SettingsFragment) {
            inSettingsFragment = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        settingsMenu = menu!!

        settingsMenu.setGroupVisible(R.id.menu_settings, true)

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

        inSettingsFragment = true

        return super.onOptionsItemSelected(item)
    }

    fun updateConversionType(conversionType: String) {
        this.conversionType = conversionType
    }

    fun getConversionType(): String {
        return conversionType
    }

    fun updateInSettingsFragment(inSettingsFragment: Boolean) {
        this.inSettingsFragment = inSettingsFragment
    }
}
