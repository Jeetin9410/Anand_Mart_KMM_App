package org.example.project.utils

fun Double.toPriceString(): String {
    val rupees = this * 10
    val formatted = (rupees * 100).toInt() / 100.0
    return "₹ ${formatted.toString().let {
        if (it.contains('.')) it else "$it.00"
    }.let {
        if (it.split('.')[1].length == 1) "${it}0" else it
    }}"
}