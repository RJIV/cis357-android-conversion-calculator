package com.chuahamilton.conversioncalculator.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent
import com.chuahamilton.conversioncalculator.util.UnitsConverter
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_conversion_home_screen.*
import org.joda.time.DateTime


class ConversionHomeScreen : Fragment() {

    private lateinit var callback: OnModeChangeListener

    fun setOnModeChangeListener(callback: OnModeChangeListener) {
        this.callback = callback
    }

    interface OnModeChangeListener {
        fun onModeChange(conversionType: String, fromUnit: String, toUnit: String)
    }

    private lateinit var conversionType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Conversion Calculator"

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversion_home_screen, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            conversionType = (arguments?.get("key") as? String)!!
            if (conversionType == "Length") {
                titleLabel.text = getString(R.string.length_converter)
            } else {
                titleLabel.text = getString(R.string.volume_converter)
            }
            fromUnits.text = (arguments?.get("fromUnit") as? String)!!
            toUnits.text = (arguments?.get("toUnit") as? String)!!

            if (arguments?.get("fromHistoryFragment") == true) {
                val historyValues = arguments?.get("vals") as Array<*>

                fromTextField.setText(historyValues[0].toString())
                toTextField.setText(historyValues[1].toString())
                titleLabel.text = historyValues[2].toString() + " Converter"
                fromUnits.text = historyValues[3].toString()
                toUnits.text = historyValues[4].toString()
            }
        }

        initializeButtons()
    }

    private fun initializeButtons() {
        calculateBtn.setOnClickListener {

            removePhoneKeypad()

            when (conversionType) {
                "Length" -> {

                    // Both fields are blank
                    if (fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        displayEmptyFieldError()
                    }

                    // If a value is in the To-Field and not in the From-Field
                    else if (fromTextField.text.isNullOrBlank() && !toTextField.text.isNullOrBlank()) {
                        convertLengthToField()
                        createHistoryItem()
                    }

                    // If a value is in the From-Field and not in the To-Field
                    else if (!fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        convertLengthFromField()
                        createHistoryItem()
                    }

                    // A value is in both the To-Field and the From-Field, the From-Field will be converted
                    else {
                        convertLengthFromField()
                        createHistoryItem()
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
                        createHistoryItem()
                    }

                    // If a value is in the From-Field and not in the To-Field
                    else if (!fromTextField.text.isNullOrBlank() && toTextField.text.isNullOrBlank()) {
                        convertVolumeFromField()
                        createHistoryItem()
                    }

                    // A value is in both the To-Field and the From-Field, the From-Field will be converted
                    else {
                        convertVolumeFromField()
                        createHistoryItem()
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
                fromUnits.text = getString(R.string.gallons)
                toUnits.text = getString(R.string.liters)
            } else {
                titleLabel.text = getString(R.string.length_converter)
                conversionType = "Length"
                fromUnits.text = getString(R.string.yards)
                toUnits.text = getString(R.string.meters)
            }
            callback.onModeChange(
                conversionType,
                fromUnits.text.toString(),
                toUnits.text.toString()
            )
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

    private fun removePhoneKeypad() {
        val inputManager = view!!
            .context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val binder = view!!.windowToken
        inputManager.hideSoftInputFromWindow(
            binder,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun createHistoryItem() {
        val item = HistoryContent.HistoryItem(
            fromTextField.text.toString().toDouble(),
            toTextField.text.toString().toDouble(),
            conversionType,
            toUnits.text.toString(),
            fromUnits.text.toString(),
            DateTime.now(),
            DateTime.now().toString(),
            "key"
        )
        HistoryContent.addItem(item)
    }
}
