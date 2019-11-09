package com.chuahamilton.conversioncalculator.fragments.dummy

import org.joda.time.DateTime
import java.util.*


object HistoryContent {
    val ITEMS: ArrayList<HistoryItem> = ArrayList()

    fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    data class HistoryItem(
        val fromVal: Double?,
        val toVal: Double?,
        val mode: String,
        val fromUnits: String,
        val toUnits: String,
        val timestamp: DateTime
    ) {

        @Override
        override fun toString(): String {
            return this.fromVal.toString() + " " + this.fromUnits + " = " + this.toVal + " " + this.toUnits
        }
    }
}

