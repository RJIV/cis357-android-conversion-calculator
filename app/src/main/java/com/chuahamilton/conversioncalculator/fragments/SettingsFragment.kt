package com.chuahamilton.conversioncalculator.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gvsu.hamilton.conversioncalculator.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        activity!!.title = "Settings"

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabIcon.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ConversionHomeScreen())
                .addToBackStack(null)
                .commit()
        }
    }


}
