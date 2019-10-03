/**
 * Handy length / volume conversion utility.  Defines enums for various units and
 * a static conversion method for both length and volume conversions.
 *
 * @author <A HREF="mailto:jonathan.engelsma@gvsu.edu">Jonathan Engelsma</A>
 * @version $Revision: 1.0 $ $Date: 2018/10/01 22:15:25 $
 */

package com.chuahamilton.conversioncalculator.util

import java.util.HashMap

object UnitsConverter {

    private var lengthConversionTable: HashMap<LengthConversionKey, Double>? = null
    private var volumeConversionTable: HashMap<VolumeConversionKey, Double>? = null

    enum class VolumeUnits {
        Liters, Gallons, Quarts
    }

    enum class LengthUnits {
        Meters, Yards, Miles
    }

    private class VolumeConversionKey internal constructor(
        internal var to: VolumeUnits,
        internal var from: VolumeUnits
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false

            val that = other as VolumeConversionKey?

            return if (from != that!!.from) false else to == that.to
        }

        override fun hashCode(): Int {
            var result = from.hashCode()
            result = 31 * result + to.hashCode()
            return result
        }
    }

    private class LengthConversionKey internal constructor(
        internal var to: LengthUnits,
        internal var from: LengthUnits
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false

            val that = other as LengthConversionKey?

            return if (from != that!!.from) false else to == that.to
        }

        override fun hashCode(): Int {
            var result = from.hashCode()
            result = 31 * result + to.hashCode()
            return result
        }
    }

    init {
        volumeConversionTable = HashMap()
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Liters, VolumeUnits.Liters)] = 1.0
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Liters, VolumeUnits.Gallons)] =
            3.78541
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Liters, VolumeUnits.Quarts)] =
            0.946353
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Gallons, VolumeUnits.Liters)] =
            0.264172
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Gallons, VolumeUnits.Gallons)] = 1.0
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Gallons, VolumeUnits.Quarts)] = 0.25
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Quarts, VolumeUnits.Liters)] =
            1.05669
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Quarts, VolumeUnits.Gallons)] = 4.0
        volumeConversionTable!![VolumeConversionKey(VolumeUnits.Quarts, VolumeUnits.Quarts)] = 1.0

        lengthConversionTable = HashMap()
        lengthConversionTable!![LengthConversionKey(LengthUnits.Meters, LengthUnits.Meters)] = 1.0
        lengthConversionTable!![LengthConversionKey(LengthUnits.Meters, LengthUnits.Yards)] = 0.9144
        lengthConversionTable!![LengthConversionKey(LengthUnits.Meters, LengthUnits.Miles)] =
            1609.34
        lengthConversionTable!![LengthConversionKey(LengthUnits.Yards, LengthUnits.Meters)] =
            1.09361
        lengthConversionTable!![LengthConversionKey(LengthUnits.Yards, LengthUnits.Yards)] = 1.0
        lengthConversionTable!![LengthConversionKey(LengthUnits.Yards, LengthUnits.Miles)] = 1760.0
        lengthConversionTable!![LengthConversionKey(LengthUnits.Miles, LengthUnits.Meters)] =
            0.000621371
        lengthConversionTable!![LengthConversionKey(LengthUnits.Miles, LengthUnits.Yards)] =
            0.000568182
        lengthConversionTable!![LengthConversionKey(LengthUnits.Miles, LengthUnits.Miles)] = 1.0

    }

    /**
     * computes volume conversions. For example:
     *
     * double lenVal = UnitsConverter.convert(10.0, VolumeUnits.Gallons,VolumeUnits.Liters)
     *
     * @param val the value to be converted
     * @param fromUnits the units of the value to be converted
     * @param toUnits the units of the converted value
     * @return the converted value
     */
    fun convert(`val`: Double?, fromUnits: VolumeUnits, toUnits: VolumeUnits): Double {
        val key = VolumeConversionKey(toUnits, fromUnits)
        return `val`!! * volumeConversionTable!![key]!!.toDouble()
    }

    /**
     * computes length conversions. For example:
     *
     * double lenVal = UnitsConverter.convert(100.0, LengthUnits.Miles,LengthUnits.Yards)
     *
     * @param val the value to be converted
     * @param fromUnits the units of the value to be converted
     * @param toUnits the units of the converted value
     * @return the converted value
     */
    fun convert(`val`: Double?, fromUnits: LengthUnits, toUnits: LengthUnits): Double {
        val key = LengthConversionKey(toUnits, fromUnits)
        return `val`!! * lengthConversionTable!![key]!!.toDouble()
    }
}