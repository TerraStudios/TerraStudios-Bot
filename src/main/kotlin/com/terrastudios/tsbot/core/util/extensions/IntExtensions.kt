package com.terrastudios.tsbot.core.util.extensions

fun Int.withinBounds(min: Int, max: Int): Boolean {
    return this in min..max
}