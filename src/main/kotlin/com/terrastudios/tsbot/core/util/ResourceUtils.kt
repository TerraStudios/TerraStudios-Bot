package com.terrastudios.tsbot.core.util

import com.beust.klaxon.Klaxon

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ResourceUtils {
    companion object {
        inline fun <reified T> getResource(path: String): T {
            return Klaxon().parse<T>(T::class.java.getResource(path).readText())!!
        }

        fun getFilePath(path: String): String {
            return this::class.java.getResource(path).path
        }

        fun getJSONObject(path: String): JSONObject {
            val parser = JSONParser()
            return parser.parse(String(Files.readAllBytes(Paths.get(this::class.java.getResource(path).toURI())))) as JSONObject
        }
    }
}