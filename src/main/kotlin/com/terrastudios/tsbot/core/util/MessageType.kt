package com.terrastudios.tsbot.core.util

import java.awt.Color

enum class MessageType(val color : Color) {
    INFO(Color.GRAY), WARNING(Color.ORANGE), ERROR(Color.RED), SUCCESS(Color.GREEN)
}