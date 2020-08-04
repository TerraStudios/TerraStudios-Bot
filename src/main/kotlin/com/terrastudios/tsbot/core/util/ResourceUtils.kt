package com.terrastudios.tsbot.core.util

import com.beust.klaxon.Klaxon

class ResourceUtils {
    companion object {
        inline fun <reified T> getResource(path : String) : T {
            return Klaxon().parse<T>(T::class.java.getResource(path).readText())!!
        }
    }
}