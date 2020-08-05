package com.terrastudios.tsbot.core.util.extensions

import java.lang.IllegalArgumentException


fun String.toBinary(): String {
    val sb = StringBuilder()

    for (character in this.toCharArray()) {
        sb.append(
            """
                ${Integer.toBinaryString(character.toInt())}
            """.trimIndent()
        ).append(" ")
    }

    return sb.toString()
}

fun String.binaryToText(): String {
    val sb = StringBuffer()

    for (num in this.split(" ")) {
        if (!num.isInt()) {
            throw IllegalArgumentException("Invalid Binary Code.")
        }
        sb.append(Character(num.toInt(2).toChar()).toString())
    }

    return sb.toString()
}

fun String.isInt(): Boolean {
    return this.toIntOrNull() != null
}