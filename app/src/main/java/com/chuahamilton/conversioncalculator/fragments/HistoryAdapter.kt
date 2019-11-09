package com.chuahamilton.conversioncalculator.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chuahamilton.conversioncalculator.fragments.HistoryFragment.OnListFragmentInteractionListener
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent.HistoryItem
import com.gvsu.hamilton.conversioncalculator.R


class HistoryAdapter(
    private val mValues: List<HistoryItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mP1.text = holder.mItem!!.toString()
        holder.mDateTime.text = holder.mItem!!.timestamp.toString()

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mP1: TextView = mView.findViewById(R.id.p1) as TextView
        val mDateTime: TextView = mView.findViewById(R.id.timestamp) as TextView
        var mItem: HistoryItem? = null

        override fun toString(): String {
            return super.toString() + " '" + mDateTime.text + "'"
        }
    }
}