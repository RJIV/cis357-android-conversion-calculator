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

    private lateinit var callback: OnUnitsChangeListener

    fun setOnSetChangeListener(callback: OnUnitsChangeListener) {
        this.callback = callback
    }

    interface OnUnitsChangeListener {
        fun onUnitsChange(fromUnit: String, toUnit: String)
    }

    private lateinit var conversionType: String
    private var fromUnit = "Yards"
    private var toUnit = "Meters"
    private var fromSpinnerPosition = 0
    private var toSpinnerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("key", "Length")?.let {
            conversionType = it
        }
        arguments?.getString("from", "Yards")?.let {
            fromUnit = it
        }
        arguments?.getString("to", "Meters")?.let {
            toUnit = it
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
        updateDefaultSpinnerUnits()
        updateUnits()

        fabIcon.setOnClickListener {
            callback.onUnitsChange(fromUnit, toUnit)
        }
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

    private fun updateDefaultSpinnerUnits(){
        if(conversionType == "Length"){
            when(fromUnit){
                "Meters" -> fromSpinnerPosition = 0
                "Yards" -> fromSpinnerPosition = 1
                "Miles" -> fromSpinnerPosition = 2
            }

            when(toUnit){
                "Meters" -> toSpinnerPosition = 0
                "Yards" -> toSpinnerPosition = 1
                "Miles" -> toSpinnerPosition = 2
            }
        }
        if(conversionType == "Volume"){
            when(fromUnit){
                "Liters" -> fromSpinnerPosition = 0
                "Gallons" -> fromSpinnerPosition = 1
                "Quarts" -> fromSpinnerPosition = 2
            }

            when(toUnit){
                "Liters" -> toSpinnerPosition = 0
                "Gallons" -> toSpinnerPosition = 1
                "Quarts" -> toSpinnerPosition = 2
            }
        }

        settingsFromSpinner.setSelection(fromSpinnerPosition)
        settingsToSpinner.setSelection(toSpinnerPosition)
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
