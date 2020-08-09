package com.terrastudios.tsbot.core.data

data class CustomCommand(
    var title: String = "",
    var text : String = "",
    var name : String,
    var imageURL : String = "",
    var color : ColorData = ColorData(10, 10, 10)

)