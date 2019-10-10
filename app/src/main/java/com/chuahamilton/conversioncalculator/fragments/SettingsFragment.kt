package com.chuahamilton.conversioncalculator.fragments


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.chuahamilton.conversioncalculator.MainActivity
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    private lateinit var conversionType: String
    private var fromUnit = "Yards"
    private var toUnit = "Meters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("key", "Length")?.let {
            conversionType = it
        }

        val mainActivity = this.activity as MainActivity

        conversionType = mainActivity.getConversionType()
        mainActivity.invalidateOptionsMenu()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Settings"

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateSpinners(conversionType)

        updateUnits()

        fabIcon.setOnClickListener {
            val conversionHomeScreen = ConversionHomeScreen()
            val bundle = bundleOf(Pair("from", fromUnit), Pair("to", toUnit))
            conversionHomeScreen.arguments = bundle

            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, conversionHomeScreen)
                .addToBackStack(null)
                .commit()
        }

        val mainActivity = activity as MainActivity
        mainActivity.updateInSettingsFragment(false)
    }

    private fun populateSpinners(conversion: String) {

        if (conversion == "Length") {

            val conversionUnits = arrayOf("Meters", "Yards", "Miles")

            if (settingsFromSpinner != null) {
                val arrayAdapter = ArrayAdapter(
                    context!!,
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                settingsFromSpinner.adapter = arrayAdapter
            }

            if (settingsToSpinner != null) {
                val arrayAdapter = ArrayAdapter(
                    context!!,
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                settingsToSpinner.adapter = arrayAdapter
            }
        }

        if (conversion == "Volume") {

            val conversionUnits = arrayOf("Liters", "Gallons", "Quarts")

            if (settingsFromSpinner != null) {
                val arrayAdapter = ArrayAdapter(
                    context!!,
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                settingsFromSpinner.adapter = arrayAdapter
            }

            if (settingsToSpinner != null) {
                val arrayAdapter = ArrayAdapter(
                    context!!,
                    R.layout.support_simple_spinner_dropdown_item,
                    conversionUnits
                )
                settingsToSpinner.adapter = arrayAdapter
            }
        }
    }

    private fun updateUnits() {

        settingsFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                fromUnit = settingsFromSpinner.selectedItem.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fromUnit = settingsFromSpinner.selectedItem.toString()
            }
        }

        settingsToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                toUnit = settingsToSpinner.selectedItem.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toUnit = settingsToSpinner.selectedItem.toString()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(R.id.menu_settings, false)
    }
}
