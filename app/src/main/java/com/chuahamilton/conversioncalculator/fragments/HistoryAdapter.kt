package com.chuahamilton.conversioncalculator.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chuahamilton.conversioncalculator.fragments.HistoryAdapter.FooterViewHolder
import com.chuahamilton.conversioncalculator.fragments.HistoryFragment.OnListFragmentInteractionListener
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent.HistoryItem
import com.chuahamilton.conversioncalculator.fragments.dummy.HistoryContent.addItem
import com.gvsu.hamilton.conversioncalculator.R
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class HistoryAdapter(
    private var items: ArrayList<HistoryItem>,
    private var mListener: OnListFragmentInteractionListener?
) :
    SectionedRecyclerViewAdapter<HistoryAdapter.HeaderViewHolder,
            HistoryAdapter.ViewHolder,
            FooterViewHolder>() {

    private var dayValues: HashMap<String, ArrayList<HistoryItem>> = HashMap()
    private var sectionHeaders: MutableList<String> = ArrayList()

    init {
        val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")

        for (historyItems in items) {
            val key = "Entries for " + fmt.print(historyItems.timestamp)
            var list: java.util.ArrayList<HistoryItem>? = this.dayValues[key]
            if (list == null) {
                list = java.util.ArrayList()
                this.dayValues[key] = list
                this.sectionHeaders.add(key)
            }
            list.add(historyItems)
        }
    }


    override fun onCreateSectionFooterViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): FooterViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history, parent, false)
        return ViewHolder(view)
    }

    @Override
    override fun onBindItemViewHolder(holder: ViewHolder, section: Int, position: Int) {
        holder.mItem = this.dayValues[this.sectionHeaders[section]]!![position]
        holder.mP1.text = holder.mItem.toString()
        holder.mDateTime.text = holder.mItem!!.timestamp.toString()
        if (holder.mItem!!.mode == "Length") {
            // length icon
            holder.mImage.setImageDrawable(holder.mImage.resources.getDrawable(R.drawable.length_icon))
        } else {
            // volume icon
            holder.mImage.setImageDrawable(holder.mImage.resources.getDrawable(R.drawable.volume_icon))
        }

        holder.mView.setOnClickListener {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener!!.onListFragmentInteraction(holder.mItem)
            }
        }
    }


    override fun getSectionCount(): Int {
        return this.sectionHeaders.size

    }

    override fun getItemCountForSection(section: Int): Int {
        return this.dayValues[this.sectionHeaders[section]]!!.size
    }

    override fun hasFooterInSection(section: Int): Boolean {
        return false
    }

    override fun onCreateSectionHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_section_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindSectionHeaderViewHolder(holder: HeaderViewHolder, section: Int) {
        holder.header.text = this.sectionHeaders[section]
    }

    override fun onBindSectionFooterViewHolder(holder: FooterViewHolder, section: Int) {

    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var header: TextView

        init {
            header = view.findViewById(R.id.header) as TextView
        }
    }

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImage = mView.findViewById(R.id.imageView) as ImageView
        val mP1: TextView = mView.findViewById(R.id.p1) as TextView
        val mDateTime: TextView = mView.findViewById(R.id.timestamp) as TextView
        var mItem: HistoryItem? = null

        override fun toString(): String {
            return super.toString() + " '" + mDateTime.text + "'"
        }
    }

    companion object {
        init {
            val now = DateTime.now()
            addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.minusDays(1)))
            addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.minusDays(1)))
            addItem(HistoryItem(2.0, 1.829, "Length", "Yards", "Meters", now.plusDays(1)))
            addItem(HistoryItem(1.0, 3.785, "Volume", "Gallons", "Liters", now.plusDays(1)))
        }
    }
}