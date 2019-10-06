package com.chuahamilton.conversioncalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chuahamilton.conversioncalculator.util.UnitsConverter
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_conversion_home_screen.*

class ConversionHomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Conversion Calculator"

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversion_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeButtons()
    }

    private fun initializeButtons() {
        calculateBtn.setOnClickListener {

            // If a value is in the To-Field and not in the From-Field
            if (fromTextField.text.isNullOrBlank() && !toTextField.text.isNullOrBlank()) {
                convertToField()
            }

            // If a value is in the From-Field and not in the To-Field
            else if (!fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                convertFromField()
            }

            // A value is in both the To-Field and the From-Field, the From-Field will be converted
            else {
                convertFromField()
            }
        }

        clearBtn.setOnClickListener {
            fromTextField.text.clear()
            toTextField.text.clear()
        }

        modeBtn.setOnClickListener {

        }
    }

    private fun convertToField() {

        val convertedNumber = UnitsConverter.convert(
            toTextField.text.toString().toDouble(),
            UnitsConverter.LengthUnits.Meters,
            UnitsConverter.LengthUnits.Yards
        )

        fromTextField.setText(convertedNumber.toString())
    }

    private fun convertFromField() {

        val convertedNumber = UnitsConverter.convert(
            fromTextField.text.toString().toDouble(),
            UnitsConverter.LengthUnits.Yards,
            UnitsConverter.LengthUnits.Meters
        )

        toTextField.setText(convertedNumber.toString())
    }

}
