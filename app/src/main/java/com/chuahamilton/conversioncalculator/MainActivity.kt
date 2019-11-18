package com.chuahamilton.conversioncalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chuahamilton.conversioncalculator.fragments.ConversionHomeScreen
import com.chuahamilton.conversioncalculator.fragments.HistoryFragment
import com.chuahamilton.conversioncalculator.fragments.SettingsFragment
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent
import com.gvsu.hamilton.conversioncalculator.R
import java.io.Serializable


class MainActivity : AppCompatActivity(), ConversionHomeScreen.OnModeChangeListener,
    ConversionHomeScreen.OnAllHistoryChangeListener,
    SettingsFragment.OnUnitsChangeListener, HistoryFragment.OnListFragmentInteractionListener {

    private var bundle = Bundle()
    private var conversionType = "Length"
    private var fromUnit = "Meters"
    private var toUnit = "Yards"
    private var inSettingsFragment = false
    private lateinit var mainMenu: Menu
    private var allHistory: ArrayList<HistoryContent.HistoryItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            this.bundle.putString("key", conversionType)
            this.bundle.putString("fromUnit", fromUnit)
            this.bundle.putString("toUnit", toUnit)
            val conversionHomeScreen = ConversionHomeScreen()
            conversionHomeScreen.arguments = this.bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, conversionHomeScreen)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is ConversionHomeScreen) {
            fragment.setOnModeChangeListener(this)
            fragment.setAllHistoryChangeListener(this)
        }

        if (fragment is SettingsFragment) {
            fragment.setOnSetChangeListener(this)
            inSettingsFragment = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        mainMenu = menu!!

        mainMenu.setGroupVisible(R.id.menu_settings, true)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        this.bundle.putString("key", conversionType)
        this.bundle.putString("from", fromUnit)
        this.bundle.putString("to", toUnit)
        this.bundle.putSerializable("allHistory", allHistory as Serializable)
        val settingsFragment = SettingsFragment()
        val historyFragment = HistoryFragment()

        if (item.toString() == "Settings") {
            settingsFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, settingsFragment)
                .addToBackStack(null)
                .commit()

            inSettingsFragment = true

            return super.onOptionsItemSelected(item)

        }

        if (item.toString() == "History") {
            historyFragment.arguments = bundle
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, historyFragment)
                .addToBackStack(null)
                .commit()

            return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateConversionType(conversionType: String, fromUnit: String, toUnit: String) {
        this.conversionType = conversionType
        this.fromUnit = fromUnit
        this.toUnit = toUnit
    }

    fun getConversionType(): String {
        return conversionType
    }

    @Override
    override fun onListFragmentInteraction(item: HistoryContent.HistoryItem?) {
        val vals = arrayOf(
            item!!.fromVal.toString(),
            item.toVal.toString(),
            item.mode,
            item.fromUnits,
            item.toUnits
        )

        this.bundle.putString("key", item.mode)

        this.bundle.putStringArray("vals", vals)
        this.bundle.putBoolean("fromHistoryFragment", true)
        val conversionHomeScreen = ConversionHomeScreen()
        conversionHomeScreen.arguments = this.bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, conversionHomeScreen)
            .addToBackStack(null)
            .commit()
    }

    override fun onUnitsChange(fromUnit: String, toUnit: String) {
        this.fromUnit = fromUnit
        this.toUnit = toUnit
        this.bundle.putString("key", conversionType)
        this.bundle.putString("fromUnit", fromUnit)
        this.bundle.putString("toUnit", toUnit)
        this.bundle.putBoolean("fromHistoryFragment", false)
        val conversionHomeScreen = ConversionHomeScreen()
        conversionHomeScreen.arguments = this.bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, conversionHomeScreen)
            .addToBackStack(null)
            .commit()
    }

    override fun onModeChange(conversionType: String, fromUnit: String, toUnit: String) {
        updateConversionType(conversionType, fromUnit, toUnit)
    }

    override fun onAllHistoryChange(allHistory: ArrayList<HistoryContent.HistoryItem>) {
        this.allHistory = allHistory
    }

}
