package com.terrastudios.tsbot.core.data

import java.awt.Color

data class ColorData(val r: Int, val g: Int, val b: Int) {
    fun getColor(): Color {
        return Color(r, g, b)
    }
}