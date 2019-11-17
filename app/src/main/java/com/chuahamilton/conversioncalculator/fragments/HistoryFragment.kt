package com.chuahamilton.conversioncalculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent.HistoryItem
import com.gvsu.hamilton.conversioncalculator.R


class HistoryFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private var allHistory: ArrayList<HistoryItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_list, container, false)
        allHistory = arguments!!.getSerializable("allHistory") as ArrayList<HistoryItem>

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (columnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, columnCount)
            }
            view.adapter = HistoryAdapter(allHistory!!, listener)
            val did = DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
            view.addItemDecoration(did)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: HistoryItem?)
    }

}
