package com.terrastudios.tsbot.core.util

import java.awt.Color

enum class MessageType(val color : Color) {
    INFO(Color(90, 150, 255)), WARNING(Color.ORANGE), ERROR(Color(220, 25, 25)), SUCCESS(Color.GREEN)
}