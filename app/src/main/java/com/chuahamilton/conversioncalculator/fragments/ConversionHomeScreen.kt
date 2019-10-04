package com.chuahamilton.conversioncalculator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_conversion_home_screen.*

class ConversionHomeScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversion_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeButtons()
    }

    private fun initializeButtons(){
        calculateBtn.setOnClickListener {

        }

        clearBtn.setOnClickListener {
            fromTextField.text.clear()
            toTextField.text.clear()
        }

        modeBtn.setOnClickListener {

        }
    }


}
