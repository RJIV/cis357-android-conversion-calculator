package com.chuahamilton.conversioncalculator.fragments.dummy

import org.joda.time.DateTime
import java.io.Serializable
import java.util.*


object HistoryContent : Serializable {
    val ITEMS: ArrayList<HistoryItem> = ArrayList()

    fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }


    data class HistoryItem(
        val fromVal: Double? = 0.0,
        val toVal: Double? = 0.0,
        val mode: String = "Length",
        val fromUnits: String = "Meters",
        val toUnits: String = "Yards",
        val timestamp: String = DateTime.now().toString(),
        val timestampString: String = "01/01/2019",
        var key: String = "key"
    ) {
        @Override
        override fun toString(): String {
            return this.fromVal.toString() + " " + this.fromUnits + " = " + this.toVal + " " + this.toUnits
        }
    }
}

