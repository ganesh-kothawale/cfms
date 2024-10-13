package `in`.porter.cfms.domain.utils.extentsions

import kotlin.math.pow

fun Double.toTwoPrecision(): Double =
  this.toBigDecimal().setScale(2).toDouble()

fun Double.toThreePrecision(): Double =
  this.times(1000.0).toInt().div(1000.0)

fun Double.toPrecision(precision: Int): Double =
  this.times(10.0.pow(precision)).toInt().div(10.0.pow(precision))

fun Double.kgsToGrams(): Double =
  this.times(1000)

fun Double.kmsToMeters(): Double =
  this.times(1000)
