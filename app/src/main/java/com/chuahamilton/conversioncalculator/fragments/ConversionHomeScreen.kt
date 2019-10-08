package com.chuahamilton.conversioncalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chuahamilton.conversioncalculator.MainActivity
import com.chuahamilton.conversioncalculator.util.UnitsConverter
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_conversion_home_screen.*


class ConversionHomeScreen : Fragment() {

    private lateinit var conversionType: String

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

        conversionType = MainActivity().getConversionType()

        if (arguments != null) {
            fromUnits.text = (arguments?.get("from") as? String)!!
            toUnits.text = (arguments?.get("to") as? String)!!
        }


        initializeButtons()
    }

    private fun initializeButtons() {
        calculateBtn.setOnClickListener {

            when (conversionType) {
                "Length" -> {

                    // Both fields are blank
                    if (fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        displayEmptyFieldError()
                    }

                    // If a value is in the To-Field and not in the From-Field
                    else if (fromTextField.text.isNullOrBlank() && !toTextField.text.isNullOrBlank()) {
                        convertLengthToField()
                    }

                    // If a value is in the From-Field and not in the To-Field
                    else if (!fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        convertLengthFromField()
                    }

                    // A value is in both the To-Field and the From-Field, the From-Field will be converted
                    else {
                        convertLengthFromField()
                    }
                }
                "Volume" -> {

                    // Both fields are empty
                    if (fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        displayEmptyFieldError()
                    }

                    // If a value is in the To-Field and not in the From-Field
                    else if (fromTextField.text.isNullOrBlank() && !toTextField.text.isNullOrBlank()) {
                        convertVolumeToField()
                    }

                    // If a value is in the From-Field and not in the To-Field
                    else if (!fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        convertVolumeFromField()
                    }

                    // A value is in both the To-Field and the From-Field, the From-Field will be converted
                    else {
                        convertVolumeFromField()
                    }
                }


            }
        }

        clearBtn.setOnClickListener {
            fromTextField.text.clear()
            toTextField.text.clear()
        }

        modeBtn.setOnClickListener {

            if (conversionType == "Length") {
                titleLabel.text = getString(R.string.volume_converter)
                conversionType = "Volume"
                MainActivity().updateConversionType(conversionType)
                fromUnits.text = getString(R.string.gallons)
                toUnits.text = getString(R.string.liters)
            } else {
                titleLabel.text = getString(R.string.length_converter)
                conversionType = "Length"
                MainActivity().updateConversionType(conversionType)
                fromUnits.text = getString(R.string.yards)
                toUnits.text = getString(R.string.meters)
            }
        }
    }

    private fun displayEmptyFieldError() {
        if (fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
            Toast.makeText(
                context!!,
                "Please enter in a value to convert",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun convertLengthToField() {
        val fromLabel = fromUnits.text.toString()
        val toLabel = toUnits.text.toString()

        val convertedNumber = UnitsConverter.convert(
            toTextField.text.toString().toDouble(),
            UnitsConverter.LengthUnits.valueOf(toLabel),
            UnitsConverter.LengthUnits.valueOf(fromLabel)
        )

        fromTextField.setText(convertedNumber.toString())
    }

    private fun convertLengthFromField() {

        val fromLabel = fromUnits.text.toString()
        val toLabel = toUnits.text.toString()

        val convertedNumber = UnitsConverter.convert(
            fromTextField.text.toString().toDouble(),
            UnitsConverter.LengthUnits.valueOf(fromLabel),
            UnitsConverter.LengthUnits.valueOf(toLabel)
        )

        toTextField.setText(convertedNumber.toString())
    }

    private fun convertVolumeToField() {
        val fromLabel = fromUnits.text.toString()
        val toLabel = toUnits.text.toString()

        val convertedNumber = UnitsConverter.convert(
            toTextField.text.toString().toDouble(),
            UnitsConverter.VolumeUnits.valueOf(toLabel),
            UnitsConverter.VolumeUnits.valueOf(fromLabel)
        )

        fromTextField.setText(convertedNumber.toString())
    }

    private fun convertVolumeFromField() {

        val fromLabel = fromUnits.text.toString()
        val toLabel = toUnits.text.toString()

        val convertedNumber = UnitsConverter.convert(
            fromTextField.text.toString().toDouble(),
            UnitsConverter.VolumeUnits.valueOf(fromLabel),
            UnitsConverter.VolumeUnits.valueOf(toLabel)
        )

        toTextField.setText(convertedNumber.toString())
    }

}
