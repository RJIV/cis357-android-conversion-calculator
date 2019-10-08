package com.chuahamilton.conversioncalculator.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_settings.*



class SettingsFragment : Fragment() {

    private var conversionType = "Length"
    private var fromUnit = "Yards"
    private var toUnit = "Meters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        conversionType = "Length"
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

        fromUnit = settingsFromSpinner.getItemAtPosition(0).toString()
        toUnit = settingsToSpinner.getItemAtPosition(0).toString()

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
    }

    private fun populateSpinners(conversion: String){

        if(conversion == "Length"){

            val conversionUnits = arrayOf("Meters", "Yards", "Miles")

            if(settingsFromSpinner != null){
                val arrayAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, conversionUnits)
                settingsFromSpinner.adapter = arrayAdapter
            }

            if(settingsToSpinner != null){
                val arrayAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, conversionUnits)
                settingsToSpinner.adapter = arrayAdapter
            }
        }

        if(conversion == "Volume"){

            val conversionUnits = arrayOf("Liters", "Gallons", "Quarts")

            if(settingsFromSpinner != null){
                val arrayAdapter = ArrayAdapter(context!!,R.layout.support_simple_spinner_dropdown_item, conversionUnits)
                settingsFromSpinner.adapter = arrayAdapter
            }

            if(settingsToSpinner != null){
                val arrayAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, conversionUnits)
                settingsToSpinner.adapter = arrayAdapter
            }
        }
    }
}
