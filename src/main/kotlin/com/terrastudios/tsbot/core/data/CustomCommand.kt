package com.terrastudios.tsbot.core.data

import com.terrastudios.tsbot.core.util.MessageType
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage

data class CustomCommand(
    var title: String = "",
    var text : String = "",
    var name : String,
    var imageURL : String = "",
    var color : ColorData = ColorData(10, 10, 10)

)