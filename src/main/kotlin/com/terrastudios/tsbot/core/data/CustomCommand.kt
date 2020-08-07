package com.terrastudios.tsbot.core.data

import java.awt.Color
import java.awt.Image

data class CustomCommand(
    val name : String,
    val color : Color,
    val image : Image,
    val url : String
)